package com.mayandro.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mayandro.local.utils.DbConstants

@Entity(tableName = DbConstants.PRODUCT_TABLE)
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "_link")
    val link: String,

    @ColumnInfo(name = "_type")
    val type: String,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "width")
    val width: Int
)


@Entity(tableName = DbConstants.REMOTE_KEYS_TABLE)
data class RemoteKeys(
    @PrimaryKey
    val id: Int,

    val prevKey: Int?,

    val nextKey: Int?
)