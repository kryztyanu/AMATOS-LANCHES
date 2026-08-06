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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        floatingActionButton = {
            if (cartCount > 0) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.toggleCart(true) },
                    containerColor = RedPrimary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(28.dp),
                    elevation = FloatingActionButtonDefaults.elevation(6.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Color(0xFFFFE29A),
                                    contentColor = Color(0xFF5D2C00)
                                ) {
                                    Text(
                                        text = "$cartCount",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = "Carrinho",
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = "Ver Pedido • ${ptBrFormat.format(totalCartPrice)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header with title, mascot logo, yellow delivery banner, and search input (Fixed at top)
            AmatosHeader(
                searchQuery = uiState.searchQuery,
                onSearchQueryChange = { viewModel.setSearchQuery(it) }
            )

            // Category Pills Tab Row (SANDUÍCHES, PASTÉIS, PETISCOS) (Fixed at top)
            CategoryTabs(
                selectedTab = uiState.activeTab,
                onTabSelected = { viewModel.selectTab(it) }
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
                    MenuTab.MACARRAO -> "MACARRÃO AO VIVO"
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
                            onFavoriteToggle = { viewModel.toggleFavorite(item.id) },
                            onAddToCart = { viewModel.openItemDetail(item) },
                            onCardClick = { viewModel.openItemDetail(item) }
                        )
                    }
                }
            }
        }
    }

    // Detail Dialog
    uiState.selectedItemDetail?.let { item ->
        if (item.category == "MACARRÃO AO VIVO" || item.id == "macarrao_1") {
            MacarraoDetailDialog(
                item = item,
                onDismiss = { viewModel.closeItemDetail() },
                onAddToCartCustom = { customItem, selectedOption, obs, qty, _ ->
                    viewModel.addToCart(customItem, selectedOption, obs, qty)
                }
            )
        } else {
            ItemDetailDialog(
                item = item,
                onDismiss = { viewModel.closeItemDetail() },
                onAddToCart = { option, obs, qty ->
                    viewModel.addToCart(item, option, obs, qty)
                }
            )
        }
    }

    // Cart Bottom Sheet
    if (uiState.isCartOpen) {
        CartBottomSheet(
            cartItems = cartItems,
            totalPrice = totalCartPrice,
            customerName = uiState.customerName,
            onCustomerNameChange = { viewModel.setCustomerName(it) },
            deliveryAddress = uiState.deliveryAddress,
            onDeliveryAddressChange = { viewModel.setDeliveryAddress(it) },
            referencePoint = uiState.referencePoint,
            onReferencePointChange = { viewModel.setReferencePoint(it) },
            orderObservation = uiState.orderObservation,
            onOrderObservationChange = { viewModel.setOrderObservation(it) },
            paymentMethod = uiState.paymentMethod,
            onPaymentMethodChange = { viewModel.setPaymentMethod(it) },
            onUpdateQuantity = { item, qty -> viewModel.updateCartQuantity(item, qty) },
            onRemoveItem = { item -> viewModel.removeFromCart(item) },
            onClearCart = { viewModel.clearCart() },
            onSendOrder = { viewModel.sendOrderViaWhatsApp(context) },
            onDismiss = { viewModel.toggleCart(false) }
        )
    }
}
