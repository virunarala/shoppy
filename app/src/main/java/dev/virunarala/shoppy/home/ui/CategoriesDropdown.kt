package dev.virunarala.shoppy.home.ui

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.virunarala.shoppy.R
import dev.virunarala.shoppy.data.model.CategoryEntity

@Composable
fun CategoriesDropdown(
    categories: List<CategoryEntity>,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onCategoryClick: (id: Long) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.all_category)) },
            onClick = {
                onCategoryClick(0L)
                onDismiss()
            }
        )
        Divider()
        categories.forEach { category ->
            DropdownMenuItem(
                text = { Text(text = category.name) },
                onClick = {
                    onCategoryClick(category.id)
                    onDismiss()
                }
            )
            if(category!=categories.last()) {
                Divider()
            }
        }
    }
}