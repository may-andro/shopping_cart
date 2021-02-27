package com.mayandro.data

import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DataSourceFactoryTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var dataSourceFactory: DataSourceFactory

    @Before
    fun setUp() {
        dataSourceFactory = DataSourceFactoryImpl(
            remoteDataSource,
            localDataSource
        )
    }
}