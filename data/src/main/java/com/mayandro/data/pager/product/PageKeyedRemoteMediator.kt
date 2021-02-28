package com.mayandro.data.pager.product

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mayandro.data.DataSourceFactory
import com.mayandro.data.mapper.ProductEntityMapper
import com.mayandro.local.database.ProductDatabase
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.entity.RemoteKeys
import com.mayandro.utility.PRODUCT_START_PAGING_INDEX
import com.mayandro.utility.exceptions.NetworkFailureException
import com.mayandro.utility.network.NetworkStatus
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

@ExperimentalPagingApi
class ProductRemoteMediator @Inject constructor(
    private val dataSourceFactory: DataSourceFactory,
    private val productDatabase: ProductDatabase,
    private val productEntityMapper: ProductEntityMapper
) : RemoteMediator<Int, ProductEntity>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ProductEntity>): MediatorResult {

        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = productDatabase.withTransaction {
                        dataSourceFactory.retrieveLocalDataStore().getLastRemoteKeys()
                    }
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }
            val page = loadKey?: PRODUCT_START_PAGING_INDEX

            return when(val response = dataSourceFactory.retrieveRemoteDataStore().getProductList(page, state.config.pageSize)){
                is NetworkStatus.Success -> {
                    val isEndOfList =  false

                    productDatabase.withTransaction {
                        // clear all tables in the database
                        if (loadType == LoadType.REFRESH) {
                            dataSourceFactory.retrieveLocalDataStore().deleteAllRemoteKeys()
                            dataSourceFactory.retrieveLocalDataStore().deleteAllProduct()
                        }

                        val prevKey = if (page == PRODUCT_START_PAGING_INDEX) null else page - 1
                        val nextKey = if (isEndOfList) null else page + 1


                        response.data?.list?.last()?.let {
                            RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                        }?.let {
                            val remoteKeyList = listOf(it)
                            dataSourceFactory.retrieveLocalDataStore().insertRemoteKeyEntity(remoteKeyList)
                        }

                        response.data?.let {
                            val productEntity = productEntityMapper.mapFromOriginalObject(it)
                            dataSourceFactory.retrieveLocalDataStore().insertProductEntity(productEntity)
                        }
                    }
                    MediatorResult.Success(endOfPaginationReached = isEndOfList)
                }
                else -> {
                    if(response.errorMessage == "The page requested does not exist") {
                        MediatorResult.Success(endOfPaginationReached = true)
                    }

                    MediatorResult.Error(NetworkFailureException(response.errorMessage?:"Error occoured"))
                }
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }
}