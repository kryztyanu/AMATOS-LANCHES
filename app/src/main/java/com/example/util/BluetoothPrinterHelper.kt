package com.example.util

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Build
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.data.model.CartItemEntity
import java.io.OutputStream
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class DiscoveredPrinter(
    val name: String,
    val address: String,
    val device: BluetoothDevice?
)

object BluetoothPrinterHelper {

    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    @SuppressLint("MissingPermission")
    fun getPairedPrinters(context: Context): List<DiscoveredPrinter> {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() ?: return emptyList()
        if (!bluetoothAdapter.isEnabled) return emptyList()

        val list = mutableListOf<DiscoveredPrinter>()
        try {
            val bonded = bluetoothAdapter.bondedDevices
            bonded?.forEach { device ->
                val name = device.name ?: "Dispositivo Desconhecido"
                list.add(DiscoveredPrinter(name = name, address = device.address, device = device))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun buildReceiptText(
        customerName: String,
        address: String,
        referencePoint: String,
        orderObservation: String,
        paymentMethod: String,
        cartItems: List<CartItemEntity>,
        totalPrice: Double
    ): String {
        val ptBrFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val formattedTotal = ptBrFormat.format(totalPrice)

        val sb = StringBuilder()
        sb.append("      ================================\n")
        sb.append("               NOVO PEDIDO:\n")
        sb.append("      ================================\n\n")
        sb.append("Cliente: ${customerName.ifBlank { "Cliente" }}\n")
        sb.append("Endereço: ${address.ifBlank { "Retirada no Balcão" }}\n")
        if (referencePoint.isNotBlank()) {
            sb.append("Ponto de Ref: $referencePoint\n")
        }
        if (orderObservation.isNotBlank()) {
            sb.append("Obs: $orderObservation\n")
        }
        sb.append("Pagamento: $paymentMethod\n")
        sb.append("--------------------------------\n")
        sb.append("ITENS DO PEDIDO:\n\n")

        cartItems.forEachIndexed { index, item ->
            val subtotal = ptBrFormat.format(item.price * item.quantity)
            sb.append("${index + 1}. ${item.quantity} ${item.name}\n")
            if (item.selectedOption.isNotBlank()) {
                sb.append("   Opção: ${item.selectedOption}\n")
            }
            if (item.observation.isNotBlank()) {
                sb.append("   Obs: ${item.observation}\n")
            }
            sb.append("   Valor: ${item.priceText} | Subtotal: $subtotal\n\n")
        }

        sb.append("--------------------------------\n")
        sb.append("TOTAL DO PEDIDO: $formattedTotal\n")
        sb.append("+ TAXA DE ENTREGA: consulte valor da taxa\n")
        sb.append("--------------------------------\n")
        sb.append("       Obrigado pelo pedido!\n\n\n\n")

        return sb.toString()
    }

    @SuppressLint("MissingPermission")
    suspend fun printToBluetoothDevice(
        device: BluetoothDevice,
        receiptText: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        var socket: BluetoothSocket? = null
        try {
            socket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            socket.connect()
            val outputStream: OutputStream = socket.outputStream

            // Reset printer / ESC/POS commands
            val initPrinter = byteArrayOf(0x1B, 0x40) // ESC @
            val alignCenter = byteArrayOf(0x1B, 0x61, 0x01) // ESC a 1
            val alignLeft = byteArrayOf(0x1B, 0x61, 0x00) // ESC a 0
            val feedAndCut = byteArrayOf(0x1D, 0x56, 0x42, 0x00) // GS V 66 0

            outputStream.write(initPrinter)
            outputStream.write(alignLeft)
            
            // Convert receipt text using CP860 or ISO-8859-1 for accented characters
            val bytes = receiptText.toByteArray(charset("ISO-8859-1"))
            outputStream.write(bytes)
            
            outputStream.write(feedAndCut)
            outputStream.flush()
            socket.close()

            Result.success(Unit)
        } catch (e: Exception) {
            try {
                socket?.close()
            } catch (_: Exception) {}
            Result.failure(e)
        }
    }

    fun printWithSystemPrinter(context: Context, receiptText: String) {
        try {
            val htmlContent = """
                <html>
                <head>
                    <style>
                        body { font-family: monospace; font-size: 14px; padding: 10px; margin: 0; }
                        h2 { text-align: center; margin-bottom: 5px; }
                        .line { border-bottom: 1px dashed #000; margin: 10px 0; }
                        .item { margin-bottom: 8px; }
                        .bold { font-weight: bold; }
                        .footer { margin-top: 15px; font-weight: bold; font-size: 15px; }
                    </style>
                </head>
                <body>
                    <pre style="white-space: pre-wrap; font-family: monospace;">$receiptText</pre>
                </body>
                </html>
            """.trimIndent()

            val webView = WebView(context)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
                    val jobName = "Pedido Amatos - Impressão"
                    val printAdapter = webView.createPrintDocumentAdapter(jobName)
                    printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())
                }
            }
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao iniciar impressão do sistema: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
