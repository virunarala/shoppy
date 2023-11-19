package dev.virunarala.shoppy.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.virunarala.shoppy.R

@Composable
fun CheckoutSummary(
    subtotal: Double,
    discount: Double,
    total: Double,
    modifier: Modifier = Modifier
) {
    val totalStr = if(total % 1.0 == 0.0) String.format("%.0f",total) else String.format("%.2f",total)
    val subtotalStr = if(subtotal % 1.0 == 0.0) String.format("%.0f",subtotal) else String.format("%.2f",subtotal)
    val discountStr = if(discount % 1.0 == 0.0) String.format("%.0f",discount) else String.format("%.2f",discount)

    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))) {

            Row(modifier = Modifier
                .fillMaxWidth()
            ) {

                Text(
                    text = stringResource(id = R.string.sub_total),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = R.string.price,subtotalStr),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Row(modifier = Modifier
                .fillMaxWidth()) {

                Text(
                    text = stringResource(id = R.string.discount),
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(text = stringResource(id = R.string.price,discountStr),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))

            Row(modifier = Modifier
                .fillMaxWidth()) {

                Text(
                    text = stringResource(id = R.string.total),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(id = R.string.price,totalStr),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}