package dev.virunarala.shoppy.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.bounceClick
import dev.virunarala.shoppy.home.data.model.Product
import dev.virunarala.shoppy.ui.theme.Orange

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onFavouriteClick: (id: Long, isFavourite: Boolean) -> Unit,
    onAddToCartClick: (id: Long) -> Unit
) {
    val price = product.price
    val priceStr = if(price % 1.0 == 0.0) String.format("%.0f",price) else String.format("%.2f",price)

    ElevatedCard(
        modifier = modifier
            .width(IntrinsicSize.Max)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()) {
                AsyncImage(
                    model = product.icon,
                    contentDescription = product.name,
                    modifier = Modifier
                        .height(60.dp)
                        .align(Alignment.Center)
                )

                val favIcon = if(product.isFavourite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                val favIconTint = if(product.isFavourite) Color.Red else Color.Gray

                Icon(
                    painter = painterResource(favIcon),
                    contentDescription = stringResource(id = R.string.add_to_favourites),
                    tint = favIconTint,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                        .bounceClick {
                            onFavouriteClick(product.id,product.isFavourite)
                        }
                )
            }

            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.padding_medium))
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            Spacer(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.padding_small))
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                .fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.product_price_per_unit,priceStr),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

                Icon(
                    painter = painterResource(id = R.drawable.ic_add_square),
                    contentDescription = stringResource(id = R.string.add_to_cart),
                    tint = Orange,
                    modifier = Modifier
                        .size(32.dp)
                        .weight(1f)
                        .bounceClick {
                            onAddToCartClick(product.id)
                        }
                )
            }

        }

    }
}