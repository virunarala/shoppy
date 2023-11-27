package dev.virunarala.shoppy.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.common.data.model.CategoryEntity
import dev.virunarala.shoppy.ui.theme.Orange

@Composable
fun CategoriesFilter(
    categories: List<CategoryEntity>,
    modifier: Modifier = Modifier,
    onCategoryClick: (id: Long) -> Unit
) {

    var showDropdown by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {

        if(showDropdown) {

            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = stringResource(id = R.string.close_categories),
                tint = Orange,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        showDropdown = false
                    }
            )

            CategoriesDropdown(
                categories = categories,
                expanded = showDropdown,
                onDismiss = { showDropdown = false },
                onCategoryClick = { id ->
                    onCategoryClick(id)
                }
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
                    .clickable {
                        showDropdown = true
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_blocks),
                    contentDescription = stringResource(id = R.string.categories),
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    text = stringResource(id = R.string.categories),
                    color = Color.White,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }


    }

}