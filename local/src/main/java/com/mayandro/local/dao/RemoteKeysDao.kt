package com.mayandro.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mayandro.local.entity.RemoteKeys
import com.mayandro.local.utils.DbConstants
import com.mayandro.local.utils.DbConstants.REMOTE_KEYS_TABLE

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query(DbConstants.QUERY_GET_KEYS_BY_ID)
    suspend fun getRemoteKeyById(id: String): RemoteKeys?

    @Query(DbConstants.QUERY_LAST_SAVED_KEY)
    suspend fun getLastSavedRemoteKey(): RemoteKeys?





    @Query(DbConstants.DELETE_ALL_KEYS)
    suspend fun clearRemoteKeys()
}