package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalPizza
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
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.MenuItem
import com.example.ui.theme.PriceRed
import com.example.ui.theme.RedPrimary
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaDetailDialog(
    item: MenuItem,
    onDismiss: () -> Unit,
    onAddToCartCustom: (item: MenuItem, selectedOption: String, observation: String, quantity: Int) -> Unit
) {
    val ptBrFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    val sabores = remember {
        listOf(
            "Calabresa Acebolada",
            "Calabresa com Presunto",
            "Frango com Requeijão",
            "Frango com Catupiry",
            "Frango com Cheddar",
            "Frango com Milho",
            "Frango com Batata Palha",
            "Portuguesa Original",
            "Bacon",
            "Queijo",
            "Mista",
            "Romeu e Julieta"
        )
    }

    var sabor1 by remember { mutableStateOf(sabores[0]) }
    var sabor2 by remember { mutableStateOf(sabores[0]) }
    var quantity by remember { mutableIntStateOf(1) }
    var observation by remember { mutableStateOf("") }

    val unitPrice = 40.0
    val totalPrice = unitPrice * quantity

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header Logo and Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.amatos_logo),
                            contentDescription = "AMATOS LANCHES Logo",
                            modifier = Modifier.height(42.dp),
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "PIZZA",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1C1B1B)
                            )
                            Text(
                                text = "R$ 40,00",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PriceRed
                            )
                        }
                    }

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

                Spacer(modifier = Modifier.height(10.dp))

                // Yellow Promo Banner
                Surface(
                    color = Color(0xFFFFF8E1),
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalPizza,
                            contentDescription = null,
                            tint = RedPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "R$ 40,00 (8 Fatias | 2 Sabores)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5D2C00)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // --- SABOR 1 ---
                    Text(
                        text = "ESCOLHA O SABOR 1:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    sabores.forEach { flavor ->
                        val isSelected = (sabor1 == flavor)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFFFFF0F0) else Color(0xFFF9F9F9))
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { sabor1 = flavor }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = flavor,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) RedPrimary else Color(0xFF212121)
                                )
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { sabor1 = flavor },
                                    colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- SABOR 2 ---
                    Text(
                        text = "ESCOLHA O SABOR 2:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    sabores.forEach { flavor ->
                        val isSelected = (sabor2 == flavor)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color(0xFFFFF0F0) else Color(0xFFF9F9F9))
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { sabor2 = flavor }
                                .padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = flavor,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) RedPrimary else Color(0xFF212121)
                                )
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { sabor2 = flavor },
                                    colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Observação
                    Text(
                        text = "Observações:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1C1B1B)
                    )
                    OutlinedTextField(
                        value = observation,
                        onValueChange = { observation = it },
                        placeholder = { Text("Ex: Refrigerante Guaraná, sem cebola...", fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Divider(color = Color(0xFFEEEEEE))

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Footer: Quantity and Add to Cart Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Quantity Controls
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F3EF))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Diminuir",
                                tint = RedPrimary
                            )
                        }

                        Text(
                            text = "$quantity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Aumentar",
                                tint = RedPrimary
                            )
                        }
                    }

                    // Add Button
                    Button(
                        onClick = {
                            val selectedOptionsText = "Sabor 1: $sabor1 | Sabor 2: $sabor2"
                            onAddToCartCustom(
                                item,
                                selectedOptionsText,
                                observation,
                                quantity
                            )
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = RedPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "ADICIONAR • ${ptBrFormat.format(totalPrice)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
