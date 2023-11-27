package dev.virunarala.shoppy.common.data.db

object Constants {
    const val DB_NAME = "shop_db"
    const val TABLE_PRODUCT = "product"
    const val TABLE_CATEGORY = "category"
    const val TABLE_CART = "cart"
}

object ProductTableColumns {
    const val ID = "id"
    const val NAME = "name"
    const val ICON = "icon"
    const val PRICE = "price"
    const val IS_FAVOURITE = "is_favourite"
    const val CATEGORY_ID = "category_id"
}

object CategoryTableColumns {
    const val ID = "id"
    const val NAME = "name"
}

object CartTableColumns {
    const val ID = "id"
    const val QUANTITY = "quantity"
}