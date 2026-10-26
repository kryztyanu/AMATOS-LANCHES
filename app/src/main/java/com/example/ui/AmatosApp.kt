package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.*
import com.example.ui.theme.PriceRed
import com.example.ui.theme.RedPrimary
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmatosApp(viewModel: MenuViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val orderHistory by viewModel.orderHistory.collectAsStateWithLifecycle()
    val totalCartPrice by viewModel.totalCartPrice.collectAsStateWithLifecycle()
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val ptBrFormat = remember { NumberFormat.getCurrencyInstance(Locale("pt", "BR")) }

    val filteredItems = remember(uiState.activeTab, uiState.searchQuery, favorites) {
        viewModel.filterMenuItems(uiState.activeTab, uiState.searchQuery, favorites)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFFCF9F8),
        floatingActionButton = {}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header with title, mascot logo, yellow delivery banner, and search input (Fixed at top)
            AmatosHeader(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { }
            )

            // Category Pills Tab Row (SANDUÍCHES, PASTÉIS, PETISCOS) (Fixed at top)
            CategoryTabs(
                selectedTab = uiState.activeTab,
                onTabSelected = { }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Category Title Header or Search Results Header
            val categoryTitle = if (uiState.searchQuery.isNotBlank()) {
                "RESULTADOS DA BUSCA (${filteredItems.size})"
            } else {
                when (uiState.activeTab) {
                    MenuTab.SANDUICHES -> "SANDUÍCHES"
                    MenuTab.PASTEIS -> "PASTÉIS"
                    MenuTab.PETISCOS -> "PETISCOS"
                    MenuTab.MACARRAO -> "MASSA"
                    MenuTab.PIZZA -> "PIZZA"
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = categoryTitle,
                    color = Color(0xFF1C1B1B),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }

            if (filteredItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum item encontrado no cardápio.",
                        color = Color(0xFF5D3F3E),
                        fontSize = 14.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredItems, key = { it.id }) { item ->
                        val isFav = favorites.contains(item.id)
                        MenuItemCard(
                            item = item,
                            isFavorite = isFav,
                            onFavoriteToggle = { },
                            onAddToCart = { },
                            onCardClick = { }
                        )
                    }
                }
            }
        }
    }

    // Modal de Alerta: Aplicativo inoperante
    AlertDialog(
        onDismissRequest = { /* Bloqueado / Inoperante */ },
        icon = {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFEBEE),
                modifier = Modifier.size(64.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Alerta",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },
        title = {
            Text(
                text = "Aviso",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color(0xFF1C1B1B),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Aplicativo inoperante, favor entrar em contato como desenvolvedor",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Todas as funcionalidades deste aplicativo foram temporariamente suspensas.",
                    fontSize = 13.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )
            }
        },
        confirmButton = {},
        dismissButton = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    )
}
