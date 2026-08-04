package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.data.model.MenuItem
import com.example.ui.theme.PriceRed
import com.example.ui.theme.RedPrimary
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailDialog(
    item: MenuItem,
    onDismiss: () -> Unit,
    onAddToCart: (selectedOption: String, observation: String, quantity: Int) -> Unit
) {
    var quantity by remember { mutableIntStateOf(1) }
    var selectedOption by remember { mutableStateOf(item.options.firstOrNull() ?: "") }
    var observation by remember { mutableStateOf("") }

    val ptBrFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }
    val totalPriceText = remember(quantity, item.price) {
        if (item.price != null) {
            ptBrFormat.format(item.price * quantity)
        } else {
            item.priceText
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Logo Image
                Image(
                    painter = painterResource(id = R.drawable.amatos_logo),
                    contentDescription = "AMATOS LANCHES Logo",
                    modifier = Modifier
                        .height(64.dp)
                        .padding(bottom = 4.dp),
                    contentScale = ContentScale.Fit
                )

                // Header title text
                Text(
                    text = "AMATOS LANCHES",
                    color = RedPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Header with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1B),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF0EDED))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = Color(0xFF1C1B1B),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price
                Text(
                    text = item.priceText,
                    color = PriceRed,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Description
                Text(
                    text = if (item.description.startsWith("(")) item.description else "(${item.description})",
                    color = Color(0xFF5D3F3E),
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )

                // Options (if any)
                if (item.options.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Escolha sua Opção:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    item.options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedOption = option }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (selectedOption == option),
                                onClick = { selectedOption = option },
                                colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = option,
                                fontSize = 14.sp,
                                color = Color(0xFF1C1B1B)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Observations
                Text(
                    text = "Observações para a cozinha:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1B1B)
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = observation,
                    onValueChange = { observation = it },
                    placeholder = { Text("Ex: Sem cebola, bem passado...", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RedPrimary,
                        unfocusedBorderColor = Color(0xFFE5E2E1)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Quantity selector and Add button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(1.dp, Color(0xFFE5E2E1), RoundedCornerShape(24.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Diminuir",
                                tint = RedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "$quantity",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1C1B1B),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Aumentar",
                                tint = RedPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            onAddToCart(selectedOption, observation, quantity)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RedPrimary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = "Adicionar • $totalPriceText",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
