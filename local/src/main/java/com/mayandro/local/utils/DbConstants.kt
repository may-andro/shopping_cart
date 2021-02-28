package com.mayandro.local.utils

object DbConstants {

    const val DATABASE_NAME = "best_secret.db"

    const val PRODUCT_TABLE = "best_secret_products"
    const val REMOTE_KEYS_TABLE = "remote_keys"

    // REMOTE_KEYS_TABLE TABLE
    const val QUERY_GET_KEYS_BY_ID = "SELECT * FROM $REMOTE_KEYS_TABLE WHERE id = :id"
    const val QUERY_LAST_SAVED_KEY = "SELECT * FROM $REMOTE_KEYS_TABLE ORDER BY id DESC LIMIT 1"
    const val DELETE_ALL_KEYS = "DELETE FROM $REMOTE_KEYS_TABLE"






    // PRODUCT TABLE
    const val QUERY_GET_ALL_PRODUCTS = "SELECT * FROM $PRODUCT_TABLE"

    const val DELETE_ALL_PRODUCT = "DELETE FROM $PRODUCT_TABLE"

    const val QUERY_GET_PRODUCT_BY_ID = "SELECT * FROM $PRODUCT_TABLE WHERE id = :id"
}