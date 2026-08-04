package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.OrderEntity
import com.example.ui.theme.PriceRed
import com.example.ui.theme.RedPrimary
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderHistoryScreen(
    orders: List<OrderEntity>,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale("pt", "BR"))
    val ptBrFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.amatos_logo),
            contentDescription = "AMATOS LANCHES Logo",
            modifier = Modifier
                .height(64.dp)
                .padding(bottom = 4.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = "AMATOS LANCHES",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = RedPrimary,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Histórico de Pedidos",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1B1B),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ReceiptLong,
                        contentDescription = "Sem pedidos",
                        tint = Color(0xFF916E6D),
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Você ainda não fez nenhum pedido.",
                        fontSize = 15.sp,
                        color = Color(0xFF5D3F3E)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(orders, key = { it.id }) { order ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFE5E2E1), RoundedCornerShape(12.dp)),
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Pedido #${order.id}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RedPrimary
                                )
                                Text(
                                    text = dateFormat.format(Date(order.timestamp)),
                                    fontSize = 12.sp,
                                    color = Color(0xFF5D3F3E)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = order.itemsSummary,
                                fontSize = 14.sp,
                                color = Color(0xFF1C1B1B),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Pagamento: ${order.paymentMethod}",
                                    fontSize = 12.sp,
                                    color = Color(0xFF5D3F3E)
                                )
                                Text(
                                    text = ptBrFormat.format(order.totalPrice),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PriceRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
