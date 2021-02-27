package com.mayandro.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.uimodel.ProductUIItem


class MockPagingSource : PagingSource<Int, ProductUIItem>(), PagerSource {
    private fun generatePost(): ProductUIItem {
        return ProductUIItem(
            id = 0,
            name = "",
            brand = "",
            price = 2,
            currency = "",
            image = "",
            link = "t",
            type = "",
            cardBackground = 2,
            width = 2,
            height = 2
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductUIItem> {
        val key = params.key ?: 0
        return LoadResult.Page(List(200) { generatePost() }.toList(), key - 1, key + 1)
    }

    // Unused in benchmark.
    override fun getRefreshKey(state: PagingState<Int, ProductUIItem>): Int? = null
}