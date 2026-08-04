package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.CartItemEntity
import com.example.ui.theme.RedPrimary
import com.example.util.BluetoothPrinterHelper
import com.example.util.DiscoveredPrinter
import kotlinx.coroutines.launch

@Composable
fun BluetoothPrinterDialog(
    customerName: String,
    deliveryAddress: String,
    referencePoint: String,
    orderObservation: String,
    paymentMethod: String,
    cartItems: List<CartItemEntity>,
    totalPrice: Double,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var printers by remember { mutableStateOf(BluetoothPrinterHelper.getPairedPrinters(context)) }
    var selectedPrinter by remember { mutableStateOf<DiscoveredPrinter?>(printers.firstOrNull()) }
    var isPrinting by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val receiptText = remember(customerName, deliveryAddress, referencePoint, orderObservation, paymentMethod, cartItems, totalPrice) {
        BluetoothPrinterHelper.buildReceiptText(
            customerName = customerName,
            address = deliveryAddress,
            referencePoint = referencePoint,
            orderObservation = orderObservation,
            paymentMethod = paymentMethod,
            cartItems = cartItems,
            totalPrice = totalPrice
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Print,
                            contentDescription = null,
                            tint = RedPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Imprimir Pedido",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1B)
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = Color.Gray
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                Text(
                    text = "Selecione uma impressora Bluetooth ou utilize o serviço de impressão do Android:",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Impressoras Pareadas (${printers.size}):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(
                        onClick = {
                            printers = BluetoothPrinterHelper.getPairedPrinters(context)
                            if (selectedPrinter == null) selectedPrinter = printers.firstOrNull()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Atualizar",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Atualizar", fontSize = 12.sp)
                    }
                }

                if (printers.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color(0xFFF7F2F2), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhuma impressora Bluetooth pareada encontrada.\nVerifique se o Bluetooth está ligado nas configurações.",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 160.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(printers) { printer ->
                            val isSelected = selectedPrinter == printer
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isSelected) Color(0xFFFFF0F0) else Color(0xFFF9F9F9),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = if (isSelected) 1.5.dp else 0.5.dp,
                                        color = if (isSelected) RedPrimary else Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedPrinter = printer }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bluetooth,
                                    contentDescription = null,
                                    tint = if (isSelected) RedPrimary else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = printer.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1C1B1B)
                                    )
                                    Text(
                                        text = printer.address,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { selectedPrinter = printer },
                                    colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                                )
                            }
                        }
                    }
                }

                statusMessage?.let { msg ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = msg,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (msg.contains("sucesso", ignoreCase = true)) Color(0xFF2E7D32) else RedPrimary,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons
                Button(
                    onClick = {
                        val printer = selectedPrinter
                        if (printer?.device == null) {
                            Toast.makeText(context, "Selecione uma impressora Bluetooth válida", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isPrinting = true
                        statusMessage = "Conectando e enviando à impressora..."
                        coroutineScope.launch {
                            val result = BluetoothPrinterHelper.printToBluetoothDevice(printer.device, receiptText)
                            isPrinting = false
                            if (result.isSuccess) {
                                statusMessage = "✅ Pedido impresso com sucesso!"
                                Toast.makeText(context, "Impresso com sucesso!", Toast.LENGTH_SHORT).show()
                            } else {
                                val error = result.exceptionOrNull()?.message ?: "Erro desconhecido"
                                statusMessage = "❌ Falha na conexão: $error"
                                Toast.makeText(context, "Erro na impressão Bluetooth: $error", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    enabled = !isPrinting && selectedPrinter != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RedPrimary)
                ) {
                    if (isPrinting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviando...", fontSize = 14.sp)
                    } else {
                        Icon(imageVector = Icons.Default.Print, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Imprimir na Impressora Bluetooth", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        BluetoothPrinterHelper.printWithSystemPrinter(context, receiptText)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Usar Gerenciador de Impressão do Android", fontSize = 12.sp, color = Color(0xFF1C1B1B))
                }
            }
        }
    }
}
