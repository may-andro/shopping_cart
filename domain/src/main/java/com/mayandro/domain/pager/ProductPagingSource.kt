package com.mayandro.domain.pager

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mayandro.data.DataSourceFactory
import com.mayandro.domain.mapper.UIProductListMapper
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.utility.PRODUCT_START_PAGING_INDEX
import com.mayandro.utility.exceptions.NetworkFailureException
import com.mayandro.utility.network.NetworkStatus
import java.lang.NullPointerException
import javax.inject.Named

class ProductPagingSource (
    private val dataSourceFactory: DataSourceFactory,
    @Named("UIProductListMapper") private val uiProductListMapper: UIProductListMapper
): PagingSource<Int, ProductUIItem>(), PagerSource {
    override fun getRefreshKey(state: PagingState<Int, ProductUIItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductUIItem> {
        return try {
            val nextPage = params.key ?: PRODUCT_START_PAGING_INDEX

            return when(val productListResponse = dataSourceFactory.retrieveRemoteDataStore().getProductList(nextPage, params.loadSize)) {
                is NetworkStatus.Success -> {
                    val response = productListResponse.data ?: throw NullPointerException()

                    LoadResult.Page(
                        data = uiProductListMapper.mapFromOriginalObject(response),
                        prevKey = if (nextPage == 1) null else nextPage - 1 ,
                        nextKey = if (params.loadSize * (nextPage + 1) <= response.size) response.page.plus(1) else null
                    )
                } is NetworkStatus.Error -> {
                    LoadResult.Error(NetworkFailureException(productListResponse.errorMessage?:""))
                } else  -> {
                    //This case will not occur here
                    LoadResult.Error(NetworkFailureException(""))
                }
            }


        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}