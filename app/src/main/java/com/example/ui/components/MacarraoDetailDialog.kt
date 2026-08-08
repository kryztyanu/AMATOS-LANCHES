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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.model.MenuItem
import com.example.ui.theme.PriceRed
import com.example.ui.theme.RedPrimary
import java.text.NumberFormat
import java.util.Locale

data class OptionItem(
    val id: String,
    val name: String,
    val imageUrl: String = "",
    val extraPrice: Double = 0.0,
    val extraText: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MacarraoDetailDialog(
    item: MenuItem,
    onDismiss: () -> Unit,
    onAddToCartCustom: (item: MenuItem, selectedOption: String, observation: String, quantity: Int, customPrice: Double) -> Unit
) {
    val ptBrFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    // Options Data
    val massas = remember {
        listOf(
            OptionItem("parafuso", "Parafuso", "https://i.ibb.co/HLHNkZwj/macarrao-parafuso.png"),
            OptionItem("penne", "Penne", "https://i.ibb.co/8n3kBwFc/macarrao-penne.png"),
            OptionItem("talharim", "Talharim", "https://i.ibb.co/cSsF4StD/macarrao-talharim.png"),
            OptionItem("spaghetti", "Spaghetti", "https://i.ibb.co/4Rqtg5Kg/macarrao-spaghetti.png")
        )
    }

    val ingredientes = remember {
        listOf(
            OptionItem("camarao", "Camarão", "https://i.ibb.co/bR2SRsGB/camarao.png", extraPrice = 5.0, extraText = "+ R$ 5,00"),
            OptionItem("carne_moida", "Carne Moída", "https://i.ibb.co/dsf3stm7/carne-moida.png"),
            OptionItem("atum", "Atum", "https://i.ibb.co/6JYKnDWP/atum.png"),
            OptionItem("bacon", "Bacon", "https://i.ibb.co/sJkdzcfX/BACON.png"),
            OptionItem("calabresa", "Calabresa", "https://i.ibb.co/skCDDzG/calabresa.png"),
            OptionItem("presunto", "Presunto", "https://i.ibb.co/8n1F0ZhT/presunto.png"),
            OptionItem("queijo", "Queijo Mussarella", "https://i.ibb.co/tMFy2k6Z/queijo.png")
        )
    }

    val temperos = remember {
        listOf(
            OptionItem("tomate", "Tomate", "https://i.ibb.co/5XPkQvNk/tomate.png"),
            OptionItem("cebola", "Cebola", "https://i.ibb.co/xKgDVxW2/CEBOLA.png"),
            OptionItem("salsa", "Salsa", "https://i.ibb.co/20Yy5LHL/salsa.png"),
            OptionItem("pimentao", "Pimentão", "https://i.ibb.co/mCKKsWt0/pimentao.png"),
            OptionItem("coentro", "Coentro", "https://i.ibb.co/LVpszqy/coentro.png"),
            OptionItem("cebolinha", "Cebolinha", "https://i.ibb.co/7JHhYSCw/cebolinha.png"),
            OptionItem("alho", "Alho", "https://i.ibb.co/twD6bqWw/ALHO.png"),
            OptionItem("passas", "Passas", "https://i.ibb.co/1pS1mF0/PASSAS.png"),
            OptionItem("azeitona", "Azeitona", "https://i.ibb.co/YT7GY445/AZEITONA.png"),
            OptionItem("ervilha", "Ervilha", "https://i.ibb.co/VWX2f7sH/ERVILHA.png"),
            OptionItem("milho", "Milho", "https://i.ibb.co/jvbF6qHR/milho.png"),
            OptionItem("oregano", "Orégano", "https://i.ibb.co/Y7hY7RJw/oregano.png"),
            OptionItem("pimenta", "Pimenta Calabresa", "https://i.ibb.co/k65q0pY0/pimenta-calabresa.png")
        )
    }

    val molhos = remember {
        listOf(
            OptionItem("branco", "Branco"),
            OptionItem("vermelho", "Vermelho"),
            OptionItem("ambos", "Ambos")
        )
    }

    // Selections
    var selectedMassa by remember { mutableStateOf("Parafuso") }
    val selectedIngredientes = remember { mutableStateMapOf<String, Boolean>() }
    val selectedTemperos = remember { mutableStateMapOf<String, Boolean>() }
    var selectedMolho by remember { mutableStateOf("Branco") }
    var quantity by remember { mutableIntStateOf(1) }
    var observation by remember { mutableStateOf("") }

    // Price Calculation
    val hasCamarao = selectedIngredientes["Camarão"] == true
    val unitPrice = 30.0 + (if (hasCamarao) 5.0 else 0.0)
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
                                text = "MACARRÃO AO VIVO",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1C1B1B)
                            )
                            Text(
                                text = "A partir de R$ 30,00",
                                fontSize = 13.sp,
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

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFE5E2E1))

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // SECTION 1: MASSA
                    Text(
                        text = "ESCOLHA O TIPO DA MASSA:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Text(
                        text = "(Escolha apenas 1 opção)",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    massas.forEach { itemOption ->
                        val isSelected = selectedMassa == itemOption.name
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFFFF0F0) else Color(0xFFF8F8F8))
                                .border(
                                    width = if (isSelected) 1.5.dp else 0.5.dp,
                                    color = if (isSelected) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedMassa = itemOption.name }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (itemOption.imageUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(itemOption.imageUrl)
                                        .setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = itemOption.name,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Text(
                                text = itemOption.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1C1B1B),
                                modifier = Modifier.weight(1f)
                            )
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedMassa = itemOption.name },
                                colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // SECTION 2: INGREDIENTES
                    Text(
                        text = "ESCOLHA OS INGREDIENTES:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Text(
                        text = "(Escolha várias opções)",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    ingredientes.forEach { itemOption ->
                        val isChecked = selectedIngredientes[itemOption.name] == true
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isChecked) Color(0xFFFFF0F0) else Color(0xFFF8F8F8))
                                .border(
                                    width = if (isChecked) 1.5.dp else 0.5.dp,
                                    color = if (isChecked) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedIngredientes[itemOption.name] = !isChecked }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (itemOption.imageUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(itemOption.imageUrl)
                                        .setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = itemOption.name,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = itemOption.name,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1C1B1B)
                                )
                                if (itemOption.extraText.isNotBlank()) {
                                    Text(
                                        text = itemOption.extraText,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = PriceRed
                                    )
                                }
                            }
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { selectedIngredientes[itemOption.name] = it },
                                colors = CheckboxDefaults.colors(checkedColor = RedPrimary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // SECTION 3: TEMPEROS
                    Text(
                        text = "ESCOLHA OS TEMPEROS:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Text(
                        text = "(Escolha várias opções)",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    temperos.forEach { itemOption ->
                        val isChecked = selectedTemperos[itemOption.name] == true
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isChecked) Color(0xFFFFF0F0) else Color(0xFFF8F8F8))
                                .border(
                                    width = if (isChecked) 1.5.dp else 0.5.dp,
                                    color = if (isChecked) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedTemperos[itemOption.name] = !isChecked }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (itemOption.imageUrl.isNotEmpty()) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(itemOption.imageUrl)
                                        .setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36")
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = itemOption.name,
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                            Text(
                                text = itemOption.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1C1B1B),
                                modifier = Modifier.weight(1f)
                            )
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { selectedTemperos[itemOption.name] = it },
                                colors = CheckboxDefaults.colors(checkedColor = RedPrimary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // SECTION 4: MOLHOS
                    Text(
                        text = "ESCOLHA OS MOLHOS:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RedPrimary
                    )
                    Text(
                        text = "(Escolha apenas 1 opção)",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    molhos.forEach { itemOption ->
                        val isSelected = selectedMolho == itemOption.name
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) Color(0xFFFFF0F0) else Color(0xFFF8F8F8))
                                .border(
                                    width = if (isSelected) 1.5.dp else 0.5.dp,
                                    color = if (isSelected) RedPrimary else Color(0xFFE0E0E0),
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedMolho = itemOption.name }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = itemOption.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1C1B1B),
                                modifier = Modifier.weight(1f)
                            )
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedMolho = itemOption.name },
                                colors = RadioButtonDefaults.colors(selectedColor = RedPrimary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // SECTION 5: OBSERVAÇÕES
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
                        placeholder = { Text("Ex: caprichar no molho...", fontSize = 13.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RedPrimary,
                            unfocusedBorderColor = Color(0xFFE5E2E1)
                        )
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFE5E2E1))

                // Footer with Quantity and Add Button
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
                            val ingList = selectedIngredientes.filterValues { it }.keys.toList()
                            val temp = selectedTemperos.filterValues { it }.keys.toList()

                            val formattedIngs = if (ingList.isNotEmpty()) {
                                ingList.joinToString(", ") { name ->
                                    if (name == "Camarão") "Camarão (+R$ 5,00)" else name
                                }
                            } else {
                                "Nenhum"
                            }

                            val formattedTemps = if (temp.isNotEmpty()) {
                                temp.joinToString(", ")
                            } else {
                                "Nenhum"
                            }

                            val details = "Massa: $selectedMassa | Ingredientes: $formattedIngs | Temperos: $formattedTemps | Molho: $selectedMolho"

                            val updatedItem = item.copy(
                                price = unitPrice,
                                priceText = ptBrFormat.format(unitPrice)
                            )

                            onAddToCartCustom(updatedItem, details, observation, quantity, unitPrice)
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
                            text = "Adicionar • ${ptBrFormat.format(totalPrice)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
