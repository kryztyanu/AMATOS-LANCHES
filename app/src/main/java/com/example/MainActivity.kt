package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.data.local.AmatosDatabase
import com.example.data.repository.MenuRepository
import com.example.ui.AmatosApp
import com.example.ui.MenuViewModel
import com.example.ui.MenuViewModelFactory
import com.example.ui.theme.AmatosLanchesTheme

class MainActivity : ComponentActivity() {

  private val database by lazy { AmatosDatabase.getDatabase(this) }
  private val repository by lazy {
    MenuRepository(
      cartDao = database.cartDao(),
      favoriteDao = database.favoriteDao(),
      orderDao = database.orderDao()
    )
  }
  private val viewModel: MenuViewModel by viewModels {
    MenuViewModelFactory(repository)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AmatosLanchesTheme {
        AmatosApp(viewModel = viewModel)
      }
    }
  }
}

