package dev.virunarala.shoppy.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.navigation.Screen
import dev.virunarala.shoppy.ui.theme.Amber
import dev.virunarala.shoppy.ui.theme.Gold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val productsUiState by viewModel.productsUiState.collectAsStateWithLifecycle()
    val groupedProducts = productsUiState.groupBy { product ->
        product.category
    }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    BadgedBox(badge = { Badge { Text(text = (cartCount ?: 0).toString()) } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_shopping_cart),
                            contentDescription = stringResource(id = R.string.favourites),
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.CartScreen.route)
                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))

                    IconButton(onClick = { navController.navigate(Screen.FavouritesScreen.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_favorite_border_24),
                            contentDescription = stringResource(id = R.string.favourites)
                        )
                    }
                },
                colors = TopAppBarDefaults
                    .topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = Color.Black,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Amber,
                                Gold,
                                Gold.copy(alpha = 0.5f)
                            )
                        )
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 160.dp,
                            bottomEnd = 160.dp
                        )
                    )
            )
        }
    ) { padding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {

            Column(
                modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)) {

                groupedProducts.forEach { (category, products) ->
                    var visible by remember { mutableStateOf(true) }
                    val categoryName = category?.name ?: stringResource(R.string.misc_category)
                    val iconId = if(visible) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down

                    Column(modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.padding_medium),
                            vertical = dimensionResource(id = R.dimen.padding_small),
                        )
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(dimensionResource(id = R.dimen.padding_small))
                                .clickable {
                                    visible = !visible
                                }
                        ) {

                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(id = iconId),
                                contentDescription = null
                            )
                        }


                        Divider(modifier = Modifier.fillMaxWidth())

                        AnimatedVisibility(visible = visible) {
                            LazyRow(
                                modifier = Modifier
                                    .padding(
                                        top = dimensionResource(id = R.dimen.padding_medium),
                                        bottom = dimensionResource(id = R.dimen.padding_medium)
                                    )
                            ) {
                                items(products) { product ->
                                    ProductItem(
                                        product = product,
                                        modifier = Modifier
                                            .padding(
                                                start = dimensionResource(id = R.dimen.padding_small),
                                                end = dimensionResource(id = R.dimen.padding_small)
                                            ),
                                        onFavouriteClick = { id,isFavourite -> viewModel.toggleFavourite(id,isFavourite) },
                                        onAddToCartClick = { id -> viewModel.addToCart(id) }
                                    )
                                }
                            }
                        }

                    }

                }
            }

            CategoriesFilter(
                categories = categories,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
                onCategoryClick = { categoryId ->

                }
            )
        }


    }
}