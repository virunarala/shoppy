package dev.virunarala.shoppy.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.virunarala.shoppy.data.db.CartDao
import dev.virunarala.shoppy.data.db.ProductDao
import dev.virunarala.shoppy.home.data.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val productDao: ProductDao,
                                              private val cartDao: CartDao) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val favouriteProductsUiState: StateFlow<List<Product>> =
        productDao.getFavourites()
            .map {
                it.map {  product ->
                    Product(
                        product.id,
                        product.name,
                        product.icon,
                        product.price,
                        product.isFavourite,
                        null
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )

    fun removeFavourite(id: Long) {
        ioScope.launch {
            productDao.setFavourite(id,0)
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