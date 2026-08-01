package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val menuItemId: String,
    val name: String,
    val category: String,
    val description: String,
    val price: Double,
    val priceText: String,
    val quantity: Int = 1,
    val selectedOption: String = "",
    val observation: String = ""
)
