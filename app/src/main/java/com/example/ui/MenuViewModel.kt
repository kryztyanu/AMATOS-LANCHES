package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.CartItemEntity
import com.example.data.model.MenuItem
import com.example.data.model.OrderEntity
import com.example.data.repository.MenuRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.Locale

enum class MenuTab {
    SANDUICHES,
    PASTEIS,
    PETISCOS,
    MACARRAO
}

data class MenuUiState(
    val activeTab: MenuTab = MenuTab.SANDUICHES,
    val searchQuery: String = "",
    val selectedItemDetail: MenuItem? = null,
    val isCartOpen: Boolean = false,
    val deliveryAddress: String = "",
    val referencePoint: String = "",
    val orderObservation: String = "",
    val paymentMethod: String = "Pix",
    val customerName: String = "",
    val isSendingOrder: Boolean = false
)

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<Set<String>> = repository.favorites
        .map { list -> list.map { it.menuItemId }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val orderHistory: StateFlow<List<OrderEntity>> = repository.orderHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalCartPrice: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val cartCount: StateFlow<Int> = cartItems.map { items ->
        items.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun selectTab(tab: MenuTab) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun openItemDetail(item: MenuItem) {
        _uiState.update { it.copy(selectedItemDetail = item) }
    }

    fun closeItemDetail() {
        _uiState.update { it.copy(selectedItemDetail = null) }
    }

    fun toggleCart(open: Boolean? = null) {
        _uiState.update { it.copy(isCartOpen = open ?: !it.isCartOpen) }
    }

    fun setCustomerName(name: String) {
        _uiState.update { it.copy(customerName = name) }
    }

    fun setDeliveryAddress(address: String) {
        _uiState.update { it.copy(deliveryAddress = address) }
    }

    fun setReferencePoint(reference: String) {
        _uiState.update { it.copy(referencePoint = reference) }
    }

    fun setOrderObservation(observation: String) {
        _uiState.update { it.copy(orderObservation = observation) }
    }

    fun setPaymentMethod(method: String) {
        _uiState.update { it.copy(paymentMethod = method) }
    }

    fun toggleFavorite(menuItemId: String) {
        viewModelScope.launch {
            val currentFavs = favorites.value
            val isFav = currentFavs.contains(menuItemId)
            repository.toggleFavorite(menuItemId, !isFav)
        }
    }

    fun addToCart(item: MenuItem, selectedOption: String = "", observation: String = "", quantity: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(item, selectedOption, observation, quantity)
        }
    }

    fun updateCartQuantity(cartItem: CartItemEntity, newQty: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(cartItem, newQty)
        }
    }

    fun removeFromCart(cartItem: CartItemEntity) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun sendOrderViaWhatsApp(context: Context) {
        val currentCart = cartItems.value
        if (currentCart.isEmpty()) {
            Toast.makeText(context, "Seu carrinho está vazio!", Toast.LENGTH_SHORT).show()
            return
        }

        val name = uiState.value.customerName.ifBlank { "Cliente" }
        val address = uiState.value.deliveryAddress.ifBlank { "Retirada no Balcão" }
        val reference = uiState.value.referencePoint
        val orderObs = uiState.value.orderObservation
        val payment = uiState.value.paymentMethod
        val total = totalCartPrice.value

        val ptBrFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val formattedTotal = ptBrFormat.format(total)

        val sb = StringBuilder()
        sb.append("*NOVO PEDIDO:*\n")
        sb.append("----------------------------------\n")
        sb.append("*Cliente:* $name\n")
        sb.append("*Endereço para Entrega:* $address\n")
        if (reference.isNotBlank()) {
            sb.append("*Ponto de Referência:* $reference\n")
        }
        if (orderObs.isNotBlank()) {
            sb.append("*Observações do Pedido:* $orderObs\n")
        }
        sb.append("*Forma de Pagamento:* $payment\n")
        sb.append("----------------------------------\n")
        sb.append("*ITENS DO PEDIDO:*\n")

        currentCart.forEachIndexed { index, item ->
            val itemTotal = ptBrFormat.format(item.price * item.quantity)
            sb.append("${index + 1}. *${item.quantity} ${item.name}* (${item.priceText})\n")
            if (item.selectedOption.isNotBlank()) {
                sb.append("   └ Opção: ${item.selectedOption}\n")
            }
            if (item.observation.isNotBlank()) {
                sb.append("   └ Obs: ${item.observation}\n")
            }
            sb.append("   Subtotal: $itemTotal\n\n")
        }

        sb.append("----------------------------------\n")
        sb.append("*TOTAL DO PEDIDO:* $formattedTotal\n")
        sb.append("+ TAXA DE ENTREGA: consulte valor da taxa\n")
        sb.append("----------------------------------\n")
        sb.append("Obrigado pelo pedido! Aguardo confirmação. 🙏")

        val summary = currentCart.joinToString { "${it.quantity} ${it.name}" }

        // Save order locally in Room
        viewModelScope.launch {
            repository.saveOrder(
                OrderEntity(
                    itemsSummary = summary,
                    totalPrice = total,
                    deliveryAddress = address,
                    paymentMethod = payment
                )
            )
            repository.clearCart()
        }

        // WhatsApp phone number: 85 98605-0960 -> 5585986050960
        val phoneNumber = "5585986050960"
        val message = URLEncoder.encode(sb.toString(), "UTF-8")
        val whatsappUrl = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$message"

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(whatsappUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            _uiState.update { it.copy(isCartOpen = false) }
        } catch (e: Exception) {
            Toast.makeText(context, "Não foi possível abrir o WhatsApp: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun filterMenuItems(tab: MenuTab, query: String, favs: Set<String>): List<MenuItem> {
        if (query.isNotBlank()) {
            return repository.getAllMenuItems().filter { item ->
                item.name.contains(query, ignoreCase = true) ||
                item.description.contains(query, ignoreCase = true) ||
                item.category.contains(query, ignoreCase = true)
            }
        }

        return when (tab) {
            MenuTab.SANDUICHES -> repository.sanduiches
            MenuTab.PASTEIS -> repository.pasteis
            MenuTab.PETISCOS -> repository.petiscos
            MenuTab.MACARRAO -> repository.macarrao
        }
    }
}

class MenuViewModelFactory(private val repository: MenuRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
