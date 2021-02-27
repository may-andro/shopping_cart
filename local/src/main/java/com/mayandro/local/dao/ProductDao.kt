package com.mayandro.local.dao

import androidx.room.*
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.utils.DbConstants

@Dao
interface ProductDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productEntity: ProductEntity)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Query(DbConstants.QUERY_GET_ALL_PRODUCTS)
    suspend fun getAllProduct(): List<ProductEntity>

    @Query(DbConstants.QUERY_GET_PRODUCT_BY_ID)
    suspend fun getProductById(id: String): ProductEntity?

    @Query(DbConstants.DELETE_ALL_PRODUCT)
    suspend fun clearProductTable()
}