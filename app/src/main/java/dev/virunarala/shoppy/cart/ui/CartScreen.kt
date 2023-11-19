package dev.virunarala.shoppy.cart.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.favourites.FavouriteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val cartProducts by viewModel.cartProductsUiState.collectAsStateWithLifecycle()
    val subtotalAmount = viewModel.getTotalAmount(cartProducts)
    val discount = viewModel.getDiscount(cartProducts)
    val totalAmount = subtotalAmount - discount

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.cart))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {

            if(cartProducts.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.cart_empty),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                        .align(Alignment.Center)
                )
            } else {
                Column(modifier = Modifier
                    .fillMaxSize()
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        items(cartProducts) { product ->
                            CartItem(
                                product = product,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(id = R.dimen.padding_small)),
                                onDecreaseQuantityClick = {
                                    viewModel.decreaseQuantity(product.id)
                                },
                                onIncreaseQuantityClick = {
                                    viewModel.increaseQuantity(product.id)
                                }
                            )
                        }
                    }

                    CheckoutSummary(
                        subtotalAmount,
                        discount,
                        totalAmount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            )
                    )

                    Spacer(modifier = Modifier
                        .height(dimensionResource(id = R.dimen.padding_small))
                    )

                    Button(
                        onClick = {
                            Toast.makeText(context,context.getString(R.string.checkout_success),Toast.LENGTH_SHORT).show()
                            viewModel.emptyCart()
                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        Text(
                            text = stringResource(id = R.string.checkout).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(
                                    vertical = dimensionResource(id = R.dimen.padding_small)
                                )
                        )
                    }

                    Spacer(modifier = Modifier
                        .height(dimensionResource(id = R.dimen.padding_small))
                    )
                }
            }
        }



    }
}