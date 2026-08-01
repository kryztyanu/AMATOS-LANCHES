package com.example.data.model

data class MenuItem(
    val id: String,
    val name: String,
    val category: String, // "SANDUÍCHES", "PASTÉIS", "PETISCOS"
    val description: String,
    val price: Double?, // null if "Consulte preços"
    val priceText: String,
    val isPopular: Boolean = false,
    val options: List<String> = emptyList() // Options like protein choice for Lasanha/Comida Baiana
)
