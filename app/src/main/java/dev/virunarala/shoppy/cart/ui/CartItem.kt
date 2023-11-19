package dev.virunarala.shoppy.cart.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.bounceClick
import dev.virunarala.shoppy.cart.data.model.CartProduct
import dev.virunarala.shoppy.ui.theme.Orange

@Composable
fun CartItem(
    product: CartProduct,
    modifier: Modifier = Modifier,
    onDecreaseQuantityClick: (id: Long) -> Unit,
    onIncreaseQuantityClick: (id: Long) -> Unit
) {

    val totalCost = product.quantity * product.price
    val totalCostStr = if(totalCost % 1.0 == 0.0) String.format("%.0f",totalCost) else String.format("%.2f",totalCost)

    ElevatedCard(modifier = modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))

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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(R.drawable.ic_remove_square),
                        contentDescription = stringResource(id = R.string.decrease_quantity),
                        tint = Orange,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_extra_small))
                            .bounceClick {
                                onDecreaseQuantityClick(product.id)
                            }
                    )

                    Text(
                        text = product.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_add_square),
                        contentDescription = stringResource(id = R.string.increase_quantity),
                        tint = Orange,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = dimensionResource(id = R.dimen.padding_extra_small))
                            .bounceClick {
                                onIncreaseQuantityClick(product.id)
                            }
                    )
                }

                Text(
                    text = stringResource(id = R.string.price,totalCostStr),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}