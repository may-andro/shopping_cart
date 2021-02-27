package com.mayandro.local.utils

object DbConstants {

    const val DATABASE_NAME = "best_secret.db"

    const val PRODUCT_TABLE = "best_secret_products"

    // PRODUCT TABLE
    const val QUERY_GET_ALL_PRODUCTS = "SELECT * FROM $PRODUCT_TABLE"

    const val DELETE_ALL_PRODUCT = "DELETE FROM $PRODUCT_TABLE"

    const val QUERY_GET_PRODUCT_BY_ID = "SELECT * FROM $PRODUCT_TABLE WHERE id = :id"
}