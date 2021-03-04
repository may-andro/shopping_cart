package com.mayandro.data

import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource
import io.mockk.*
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataSourceFactoryTest {
    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()

    @Test
    fun getLocalDataSource() {
        val dataSourceFactory = mockk<DataSourceFactoryImpl>()
        //STUB calls
        every { dataSourceFactory.retrieveLocalDataStore() } returns localDataSource
        //Execute the code
        val result = dataSourceFactory.retrieveLocalDataStore()
        //Verify
        verify { dataSourceFactory.retrieveLocalDataStore() }

        assertEquals(localDataSource, result)
    }

    @Test
    fun getRemoteDataSource() {
        val dataSourceFactory = mockk<DataSourceFactoryImpl>()
        //STUB calls
        every { dataSourceFactory.retrieveRemoteDataStore() } returns remoteDataSource
        //Execute the code
        val result = dataSourceFactory.retrieveRemoteDataStore()
        //Verify
        verify { dataSourceFactory.retrieveRemoteDataStore() }

        assertEquals(remoteDataSource, result)
    }
}