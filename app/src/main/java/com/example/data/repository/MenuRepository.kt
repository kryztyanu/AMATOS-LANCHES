package com.example.data.repository

import com.example.data.local.CartDao
import com.example.data.local.FavoriteDao
import com.example.data.local.OrderDao
import com.example.data.model.CartItemEntity
import com.example.data.model.FavoriteEntity
import com.example.data.model.MenuItem
import com.example.data.model.OrderEntity
import kotlinx.coroutines.flow.Flow

class MenuRepository(
    private val cartDao: CartDao,
    private val favoriteDao: FavoriteDao,
    private val orderDao: OrderDao
) {

    val cartItems: Flow<List<CartItemEntity>> = cartDao.getAllCartItems()
    val favorites: Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
    val orderHistory: Flow<List<OrderEntity>> = orderDao.getAllOrders()

    suspend fun addToCart(item: MenuItem, selectedOption: String = "", observation: String = "", quantity: Int = 1) {
        val priceVal = item.price ?: 0.0
        cartDao.insertCartItem(
            CartItemEntity(
                menuItemId = item.id,
                name = item.name,
                category = item.category,
                description = item.description,
                price = priceVal,
                priceText = item.priceText,
                quantity = quantity,
                selectedOption = selectedOption,
                observation = observation
            )
        )
    }

    suspend fun updateCartQuantity(cartItem: CartItemEntity, newQuantity: Int) {
        if (newQuantity <= 0) {
            cartDao.deleteCartItem(cartItem)
        } else {
            cartDao.updateCartItem(cartItem.copy(quantity = newQuantity))
        }
    }

    suspend fun removeFromCart(cartItem: CartItemEntity) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun toggleFavorite(menuItemId: String, isFav: Boolean) {
        if (isFav) {
            favoriteDao.addFavorite(FavoriteEntity(menuItemId))
        } else {
            favoriteDao.removeFavorite(menuItemId)
        }
    }

    suspend fun saveOrder(order: OrderEntity): Long {
        return orderDao.insertOrder(order)
    }

    fun getAllMenuItems(): List<MenuItem> {
        return sanduiches + pasteis + petiscos
    }

    // --- FULL MENU LIST FROM AMATOS LANCHES ---

    val sanduiches = listOf(
        MenuItem("sand_1", "Simples", "SANDUÍCHES", "pão e hambúrguer bovino", 8.0, "R$ 8,00"),
        MenuItem("sand_2", "Burguer", "SANDUÍCHES", "pão, hambúrguer bovino e queijo", 9.0, "R$ 9,00"),
        MenuItem("sand_3", "Misto", "SANDUÍCHES", "pão, queijo e presunto", 9.0, "R$ 9,00"),
        MenuItem("sand_4", "Misto Burguer", "SANDUÍCHES", "pão, hambúrguer bovino, queijo e presunto", 17.0, "R$ 17,00"),
        MenuItem("sand_5", "Misto Calabresa", "SANDUÍCHES", "pão, queijo, presunto e calabresa", 19.0, "R$ 19,00"),
        MenuItem("sand_6", "Misto Bacon", "SANDUÍCHES", "pão, queijo, presunto e bacon", 20.0, "R$ 20,00"),
        MenuItem("sand_7", "Misto Frango", "SANDUÍCHES", "pão, queijo, presunto e frango", 20.0, "R$ 20,00"),
        MenuItem("sand_8", "Baurú", "SANDUÍCHES", "pão, queijo, tomate e orégano", 8.0, "R$ 8,00"),
        MenuItem("sand_9", "Americano 1", "SANDUÍCHES", "pão, queijo, presunto, ovo e salada", 15.0, "R$ 15,00"),
        MenuItem("sand_10", "Americano 2", "SANDUÍCHES", "pão, hambúrguer, queijo, bacon e salada", 16.0, "R$ 16,00"),
        MenuItem("sand_11", "Americano 3", "SANDUÍCHES", "pão, hambúrguer, queijo, bacon, salada e ovo", 18.0, "R$ 18,00"),
        MenuItem("sand_12", "X-Frango", "SANDUÍCHES", "pão, frango, queijo e salada", 21.0, "R$ 21,00"),
        MenuItem("sand_13", "X-Frango Tudo", "SANDUÍCHES", "pão, frango, queijo, ovo, bacon, calabresa e salada", 25.0, "R$ 25,00", isPopular = true),
        MenuItem("sand_14", "Amatos Frango", "SANDUÍCHES", "pão, filé de frango, queijo coalho e salada", 26.0, "R$ 26,00"),
        MenuItem("sand_15", "Amatos Alcatra", "SANDUÍCHES", "pão, filé de alcatra, queijo coalho e salada", 28.0, "R$ 28,00", isPopular = true),
        MenuItem("sand_16", "Amatos Bacon", "SANDUÍCHES", "pão, hambúrguer bovino, bacon, cheddar e salada", 24.0, "R$ 24,00"),
        MenuItem("sand_17", "X-Bacon", "SANDUÍCHES", "pão, bacon, queijo e salada", 22.0, "R$ 22,00"),
        MenuItem("sand_18", "X-Bacon Egg", "SANDUÍCHES", "pão, bacon, queijo, ovo e salada", 23.0, "R$ 23,00"),
        MenuItem("sand_19", "X-Bacon Frango", "SANDUÍCHES", "pão, bacon, queijo, frango e salada", 25.0, "R$ 25,00"),
        MenuItem("sand_20", "X-Calabresa", "SANDUÍCHES", "pão, hambúrguer, queijo, calabresa e salada", 23.0, "R$ 23,00"),
        MenuItem("sand_21", "Mistão", "SANDUÍCHES", "pão, filé de alcatra, filé de frango, calabresa, queijo, presunto e salada", 33.0, "R$ 33,00", isPopular = true),
        MenuItem("sand_22", "X-3 Queijos", "SANDUÍCHES", "pão, hambúrguer bovino, mussarela, catupiry e cheddar", 22.0, "R$ 22,00"),
        MenuItem("sand_23", "X-Egg", "SANDUÍCHES", "pão, hambúrguer bovino, queijo e ovo", 18.0, "R$ 18,00"),
        MenuItem("sand_24", "X-Salada", "SANDUÍCHES", "pão, hambúrguer bovino, queijo e salada", 19.0, "R$ 19,00"),
        MenuItem("sand_25", "X-Salada Egg", "SANDUÍCHES", "pão, hambúrguer bovino, queijo, salada e ovo", 20.0, "R$ 20,00"),
        MenuItem("sand_26", "X-Omelete", "SANDUÍCHES", "pão, queijo, presunto, cebola e ovo", 20.0, "R$ 20,00"),
        MenuItem("sand_27", "X-Tudo 1", "SANDUÍCHES", "pão, hambúrguer, queijo, presunto, ovo, bacon, calabresa e salada", 21.0, "R$ 21,00"),
        MenuItem("sand_28", "X-Tudo 2", "SANDUÍCHES", "pão, 2 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", 22.0, "R$ 22,00"),
        MenuItem("sand_29", "X-Tudo 3", "SANDUÍCHES", "pão, 3 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", 23.0, "R$ 23,00"),
        MenuItem("sand_30", "X-Tudo 4", "SANDUÍCHES", "pão, 4 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", 24.0, "R$ 24,00", isPopular = true),
        MenuItem("sand_31", "X-Frango Calabresa", "SANDUÍCHES", "pão, frango, calabresa, queijo e salada", 21.0, "R$ 21,00"),
        MenuItem("sand_32", "X-Frango Bacon", "SANDUÍCHES", "pão, frango, bacon, queijo e salada", 22.0, "R$ 22,00"),
        MenuItem("sand_33", "X-Gostoso Frango", "SANDUÍCHES", "pão, frango, hambúrguer bovino, queijo e tomate", 22.0, "R$ 22,00"),
        MenuItem("sand_34", "X-Churrasco 1", "SANDUÍCHES", "pão francês, filé bovino, queijo e cebola", 25.0, "R$ 25,00"),
        MenuItem("sand_35", "X-Churrasco 2", "SANDUÍCHES", "pão francês, filé bovino, queijo, bacon, ovo, cebola e salada", 32.0, "R$ 32,00"),
        MenuItem("sand_36", "X-Light", "SANDUÍCHES", "pão, frango, queijo e salada", 21.0, "R$ 21,00"),
        MenuItem("sand_37", "X-Gordo", "SANDUÍCHES", "pão, calabresa, 2 hambúrgueres, ovo, cebola, tomate e orégano", 23.0, "R$ 23,00"),
        MenuItem("sand_38", "X-Especial", "SANDUÍCHES", "pão, frango, hambúrguer, queijo, presunto, ovo, bacon e calabresa", 26.0, "R$ 26,00"),
        MenuItem("sand_39", "X-Frango com Cheddar", "SANDUÍCHES", "pão, frango, queijo, cheddar e salada", 23.0, "R$ 23,00")
    )

    val pasteis = listOf(
        MenuItem("past_1", "Barroso", "PASTÉIS", "frango com queijo", 22.0, "R$ 22,00"),
        MenuItem("past_2", "Palmeiras", "PASTÉIS", "frango com presunto", 21.0, "R$ 21,00"),
        MenuItem("past_3", "Bulevar", "PASTÉIS", "frango com cheddar", 24.0, "R$ 24,00"),
        MenuItem("past_4", "Itaperi", "PASTÉIS", "frango com catupiry", 24.0, "R$ 24,00"),
        MenuItem("past_5", "Mondubim", "PASTÉIS", "frango com bacon", 23.0, "R$ 23,00"),
        MenuItem("past_6", "Itaitinga", "PASTÉIS", "frango, queijo e presunto", 23.0, "R$ 23,00"),
        MenuItem("past_7", "Estrada do Fio", "PASTÉIS", "carne com queijo", 22.0, "R$ 22,00"),
        MenuItem("past_8", "Messejana", "PASTÉIS", "carne, queijo e ovo", 23.0, "R$ 23,00"),
        MenuItem("past_9", "Parque Iracema", "PASTÉIS", "carne moída", 20.0, "R$ 20,00"),
        MenuItem("past_10", "Pedras", "PASTÉIS", "carne do sol com queijo", 30.0, "R$ 30,00", isPopular = true),
        MenuItem("past_11", "Caucaia", "PASTÉIS", "queijo, presunto e ovo", 21.0, "R$ 21,00"),
        MenuItem("past_12", "Av. Perimental", "PASTÉIS", "presunto com milho", 21.0, "R$ 21,00"),
        MenuItem("past_13", "Conj. Palmeiras", "PASTÉIS", "presunto e queijo", 19.0, "R$ 19,00"),
        MenuItem("past_14", "Santa Filomena", "PASTÉIS", "atum com palmito", 24.0, "R$ 24,00"),
        MenuItem("past_15", "São Cristóvão", "PASTÉIS", "presunto e queijo", 19.0, "R$ 19,00"),
        MenuItem("past_16", "Pq. 2 irmãos", "PASTÉIS", "carne do sol, banana da terra e queijo", 35.0, "R$ 35,00", isPopular = true),
        MenuItem("past_17", "Luiz Gonzaga", "PASTÉIS", "atum com queijo", 23.0, "R$ 23,00"),
        MenuItem("past_18", "Parquelândia", "PASTÉIS", "calabresa com queijo", 23.0, "R$ 23,00"),
        MenuItem("past_19", "Bairro Novo", "PASTÉIS", "calabresa, carne, queijo, presunto e frango", 25.0, "R$ 25,00"),
        MenuItem("past_20", "Ancuri", "PASTÉIS", "filé de alcatra ou maminha com queijo", 27.0, "R$ 27,00"),
        MenuItem("past_21", "Jangurussú", "PASTÉIS", "bacon com queijo", 23.0, "R$ 23,00"),
        MenuItem("past_22", "Vl. Manoel Sátiro", "PASTÉIS", "bacon, carne e queijo", 24.0, "R$ 24,00"),
        MenuItem("past_23", "Sabiaguaba", "PASTÉIS", "camarão com queijo", 30.0, "R$ 30,00"),
        MenuItem("past_24", "Parangaba", "PASTÉIS", "camarão com cheddar", 30.0, "R$ 30,00"),
        MenuItem("past_25", "Maraponga", "PASTÉIS", "camarão com cream cheese", 32.0, "R$ 32,00", isPopular = true),
        MenuItem("past_26", "Granja Lisboa", "PASTÉIS", "camarão com catupiry", 31.0, "R$ 31,00"),
        MenuItem("past_27", "Praia do Futuro", "PASTÉIS", "lombinho com queijo e milho", 25.0, "R$ 25,00"),
        MenuItem("past_28", "Violeta", "PASTÉIS", "lombinho com cream cheese", 26.0, "R$ 26,00"),
        MenuItem("past_29", "Zé Walter", "PASTÉIS", "queijo", 35.0, "R$ 35,00"),
        MenuItem("past_30", "Lagoa Redonda", "PASTÉIS", "queijo com goiabada", 20.0, "R$ 20,00"),
        MenuItem("past_31", "Curió", "PASTÉIS", "presunto, queijo, ovo, azeitona e cebola", 24.0, "R$ 24,00"),
        MenuItem("past_32", "Damas", "PASTÉIS", "frango, passas, milho e ervilha", 24.0, "R$ 24,00"),
        MenuItem("past_33", "Varjota", "PASTÉIS", "carne, passas, pim. malagueta ou pim. calabresa", 23.0, "R$ 23,00"),
        MenuItem("past_34", "Meireles", "PASTÉIS", "brocólis, palmito, tomate, cereja e peito de peru", 25.0, "R$ 25,00"),
        MenuItem("past_35", "B. do Ceará", "PASTÉIS", "carne e frango", 24.0, "R$ 24,00"),
        MenuItem("past_36", "Passare (Pastel Japonês)", "PASTÉIS", "camarão, kane kamma e cream cheese", 35.0, "R$ 35,00"),
        MenuItem("past_37", "Vila Velha", "PASTÉIS", "ovo, queijo e presunto", 21.0, "R$ 21,00"),
        MenuItem("past_38", "Pirambu", "PASTÉIS", "frango, milho e ervilha", 22.0, "R$ 22,00"),
        MenuItem("past_39", "Aldeota", "PASTÉIS", "carne do sol, queijo e purê de aipim", 35.0, "R$ 35,00", isPopular = true),
        MenuItem("past_40", "Castelo Encantado", "PASTÉIS", "frango com requeijão cremoso", 24.0, "R$ 24,00"),
        MenuItem("past_41", "Caça e Pesca", "PASTÉIS", "carne do sol, macaxeira frita e queijo coalho", 30.0, "R$ 30,00")
    )

    val petiscos = listOf(
        MenuItem("pet_1", "Caixa da Felicidade", "PETISCOS", "(Batata Frita, Contra Filé, Toscana, Asinha, Coxinha da Asa, Arroz à Grega, Salada de Maionese, Baião Cremoso, Farofa e Vinagrete)", 105.0, "R$ 105,00", isPopular = true),
        MenuItem("pet_2", "Baião Cremoso", "PETISCOS", "(acompanha: Carne do sol)", 60.0, "R$ 60,00"),
        MenuItem("pet_3", "Pirão de Aipim (Camarão)", "PETISCOS", "(acompanha: Macaxeira, Camarão, Queijo Ralado e Vinagrete)", 70.0, "R$ 70,00"),
        MenuItem("pet_4", "Pirão de Aipim (Carne do Sol)", "PETISCOS", "(acompanha: Macaxeira, Carne do Sol, Queijo Ralado e Vinagrete)", 60.0, "R$ 60,00"),
        MenuItem("pet_5", "Escondidinho (Camarão)", "PETISCOS", "(acompanha: Macaxeira, Camarão e Mussarela Ralada)", 80.0, "R$ 80,00"),
        MenuItem("pet_6", "Escondidinho (Carne do Sol)", "PETISCOS", "(acompanha: Macaxeira, Carne do Sol e Mussarela Ralada)", 70.0, "R$ 70,00"),
        MenuItem("pet_7", "Lasanha", "PETISCOS", "(opções: Carne ou Frango ou Queijo e Presunto)", 40.0, "R$ 40,00", options = listOf("Carne", "Frango", "Queijo e Presunto")),
        MenuItem("pet_8", "Comida Baiana", "PETISCOS", "(acompanha: Arroz, Caruru, Feijão Fradinho, Vatapá e Farofa - opções de proteína: Peixe ou Frango)", null, "Consulte preços", options = listOf("Peixe", "Frango")),
        MenuItem("pet_9", "Bobó de Camarão", "PETISCOS", "(porção única)", 75.0, "R$ 75,00"),
        MenuItem("pet_10", "Batata Frita Simples", "PETISCOS", "(porção tradicional)", 30.0, "R$ 30,00"),
        MenuItem("pet_11", "Batata Frita com Calabresa", "PETISCOS", "(acompanha calabresa fatiada)", 33.0, "R$ 33,00"),
        MenuItem("pet_12", "Batata Frita com Bacon e Cheddar", "PETISCOS", "(coberta com molho cheddar e cubos de bacon)", 35.0, "R$ 35,00", isPopular = true)
    )
}
