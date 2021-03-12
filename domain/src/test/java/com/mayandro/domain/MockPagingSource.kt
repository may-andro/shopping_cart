package com.mayandro.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mayandro.domain.pager.PagerSource
import com.mayandro.remote.model.ProductItem


class MockPagingSource : PagingSource<Int, ProductItem>(), PagerSource {
    private fun generatePost(): ProductItem {
        return ProductItem(
            id = 0,
            name = "",
            brand = "",
            price = 2,
            currency = "",
            image = "",
            _link = "t",
            _type = "",
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductItem> {
        val key = params.key ?: 0
        return LoadResult.Page(List(200) { generatePost() }.toList(), key - 1, key + 1)
    }

    // Unused in benchmark.
    override fun getRefreshKey(state: PagingState<Int, ProductItem>): Int? = null
}