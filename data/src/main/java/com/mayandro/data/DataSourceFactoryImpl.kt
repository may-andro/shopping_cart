package com.mayandro.data

import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource
import javax.inject.Inject

class DataSourceFactoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): DataSourceFactory {
    override fun retrieveLocalDataStore(): LocalDataSource {
        return localDataSource
    }

    override fun retrieveRemoteDataStore(): RemoteDataSource {
        return remoteDataSource
    }
}