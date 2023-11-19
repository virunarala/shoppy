package dev.virunarala.shoppy.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
fun FavouriteItem(
    product: Product,
    modifier: Modifier = Modifier,
    onRemoveFavouriteClicked: (id: Long) -> Unit,
    onAddToCartClick: (id: Long) -> Unit
) {
    ElevatedCard(modifier = modifier) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            AsyncImage(
                model = product.icon,
                contentDescription = product.name,
                modifier = Modifier
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_medium)))

            Column {
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.product_price_per_unit, product.price.toString()),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
            ) {

                Icon(
                    painter = painterResource(R.drawable.baseline_favorite_24),
                    contentDescription = stringResource(id = R.string.add_to_favourites),
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .bounceClick {
                            onRemoveFavouriteClicked(product.id)
                        }
                )

                Icon(
                    painter = painterResource(R.drawable.ic_add_square),
                    contentDescription = stringResource(id = R.string.add_to_cart),
                    tint = Orange,
                    modifier = Modifier
                        .size(24.dp)
                        .bounceClick {
                            onAddToCartClick(product.id)
                        }
                )

            }
        }

    }
}