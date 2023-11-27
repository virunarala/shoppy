package dev.virunarala.shoppy.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.virunarala.shoppy.cart.data.model.CartProduct
import dev.virunarala.shoppy.common.data.db.CartDao
import dev.virunarala.shoppy.common.data.db.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao, private val productDao: ProductDao): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val ioScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val cartEntitiesFlow = cartDao.getAll()
    private val productEntitiesFlow = cartEntitiesFlow.flatMapLatest { cartEntities ->
        val ids = cartEntities.map { cartEntity -> cartEntity.id }
        productDao.findAllByIds(ids.toLongArray())
    }
    val cartProductsUiState: StateFlow<List<CartProduct>> =
        cartEntitiesFlow
            .combine(productEntitiesFlow) { cartEntities, productEntities ->
                val cartProducts = mutableListOf<CartProduct>()
                if(cartEntities.isEmpty()) {
                    //No Items in cart
                    cartProducts
                } else {
                    productEntities.forEach { product ->
                        val cartEntity = cartEntities.find { it.id == product.id }
                        cartEntity?.let {
                            cartProducts.add(
                                CartProduct(
                                    id = product.id,
                                    name = product.name,
                                    icon = product.icon,
                                    price = product.price,
                                    isFavourite = product.isFavourite,
                                    quantity = cartEntity.quantity
                                )
                            )
                        }

                    }
                    cartProducts
                }

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = emptyList()
            )


    fun decreaseQuantity(id: Long) {
        ioScope.launch {
            val cartEntities = cartDao.findById(id)
            if(cartEntities.isEmpty()) {
                // Product not in cart.
            } else {
                val cartEntity = cartEntities.first()
                val quantity = cartEntity.quantity
                if(quantity<=1) {
                    //If decreasing below 1, delete the entry
                    cartDao.delete(id)
                } else {
                    cartDao.decreaseQuantity(id)
                }
            }
        }
    }

    fun increaseQuantity(id: Long) {
        ioScope.launch {
            cartDao.increaseQuantity(id)
        }
    }

    fun emptyCart() {
        ioScope.launch {
            cartDao.deleteAll()
        }
    }

    fun getTotalAmount(cartProducts: List<CartProduct>): Double {
        var totalAmount: Double = 0.0
        cartProducts.forEach { cartProduct ->
            Timber.i("Cart Product: $cartProduct")
            totalAmount += cartProduct.price * cartProduct.quantity
        }
        Timber.i("Total Amount: $totalAmount")
        return totalAmount
    }

    fun getDiscount(cartProducts: List<CartProduct>): Double {
        return getTotalAmount(cartProducts) * 0.15
    }

    override fun onCleared() {
        super.onCleared()
        ioScope.cancel()
    }
}





