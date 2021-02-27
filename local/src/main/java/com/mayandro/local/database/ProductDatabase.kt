package com.mayandro.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayandro.local.dao.ProductDao
import com.mayandro.local.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao
}