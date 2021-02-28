package com.mayandro.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.local.entity.ProductEntity


class MockPagingSource : PagingSource<Int, ProductEntity>(), PagerSource {
    private fun generatePost(): ProductEntity {
        return ProductEntity(
            id = 0,
            name = "",
            brand = "",
            price = 2,
            currency = "",
            image = "",
            link = "t",
            type = "",
            0,0

        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        val key = params.key ?: 0
        return LoadResult.Page(List(200) { generatePost() }.toList(), key - 1, key + 1)
    }

    // Unused in benchmark.
    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? = null
}