package com.mayandro.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayandro.local.dao.ProductDao
import com.mayandro.local.dao.RemoteKeysDao
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.entity.RemoteKeys

@Database(entities = [ProductEntity::class, RemoteKeys::class], version = 4, exportSchema = false)
abstract class ProductDatabase: RoomDatabase(){
    abstract fun productDao(): ProductDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}