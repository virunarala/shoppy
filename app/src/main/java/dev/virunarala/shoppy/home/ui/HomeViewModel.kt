package dev.virunarala.shoppy.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.virunarala.shoppy.data.db.CartDao
import dev.virunarala.shoppy.data.db.CategoryDao
import dev.virunarala.shoppy.data.db.ProductDao
import dev.virunarala.shoppy.data.model.CategoryEntity
import dev.virunarala.shoppy.home.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productDao: ProductDao,
                                        private val categoryDao: CategoryDao,
                                        private val cartDao: CartDao): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val cartCount: StateFlow<Int?> =
        cartDao.getCount()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = null
            )

    val categories: StateFlow<List<CategoryEntity>> =
        categoryDao.getAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    val productsUiState: StateFlow<List<Product>> =
        productDao.getAll()
            .combine(categoryDao.getAll()) { productEntities, categoryEntities ->
                val products = mutableListOf<Product>()
                productEntities.forEach { product ->
                    val category = categoryEntities.find { it.id == product.categoryId }
                    products.add(
                        Product(
                            id = product.id,
                            name = product.name,
                            icon = product.icon,
                            price = product.price,
                            isFavourite = product.isFavourite,
                            category = category
                        )
                    )
                }
                products
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    fun toggleFavourite(id: Long, isFavourite: Boolean) {
        ioScope.launch {
            val flag = if(isFavourite) 0 else 1
            productDao.setFavourite(id,flag)
        }
    }

    fun addToCart(id: Long) {
        ioScope.launch {
            cartDao.addToCart(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}





