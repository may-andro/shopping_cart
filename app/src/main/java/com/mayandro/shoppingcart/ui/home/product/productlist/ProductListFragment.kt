package com.mayandro.shoppingcart.ui.home.product.productlist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mayandro.local.entity.ProductEntity
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.FragmentProductListBinding
import com.mayandro.shoppingcart.ui.base.BaseFragment
import com.mayandro.shoppingcart.ui.home.product.productlist.adapter.ProductLoadStateAdapter
import com.mayandro.shoppingcart.ui.home.product.productlist.adapter.ProductPagerAdapter
import com.mayandro.shoppingcart.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductListViewModel>(), ProductListInteractor {
    var spanCount = 2

    private val productPagerAdapter: ProductPagerAdapter by lazy { ProductPagerAdapter() }

    lateinit var startSet: ConstraintSet
    lateinit var endSet: ConstraintSet

    override fun getViewModelClass() = ProductListViewModel::class.java

    override fun getViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val orientation = resources.configuration.orientation
        spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            2
        } else {
            4
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewInteractor = this


        startSet = binding.motionLayout.getConstraintSet(R.layout.fragment_product_list)
        endSet = binding.motionLayout.getConstraintSet(R.layout.fragment_product_list_end)

        seyUpRecyclerViewAdapter()
        setUpRecyclerView()

        lifecycleScope.launch {
            viewModel.productList.collectLatest {
                productPagerAdapter.submitData(it)
            }
        }
    }

    private fun seyUpRecyclerViewAdapter() {
        // show the loading state for te first load
        productPagerAdapter.addLoadStateListener { loadState ->
            binding.apply {
                enableView(binding.progressBar, R.id.progressBar, loadState.source.refresh is LoadState.Loading)
                enableView(binding.recyclerView, R.id.recyclerView, loadState.source.refresh is LoadState.NotLoading)
                enableView(binding.retryButton, R.id.retryButton, loadState.source.refresh is LoadState.Error)
                enableView(binding.textViewError, R.id.textViewError, loadState.source.refresh is LoadState.Error)

                when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }?.let {
                    textViewError.text = it.error.message!!
                }
                //Empty Button
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && productPagerAdapter.itemCount < 1) {
                    enableView(binding.recyclerView, R.id.recyclerView, false)
                    enableView(binding.textViewError, R.id.textViewError, true)
                    textViewError.text = "No data found. List is empty"
                } else {
                    enableView(binding.textViewError, R.id.textViewError, false)
                }
            }
        }
        productPagerAdapter.onItemClickListener = object : ProductPagerAdapter.OnItemClickListener {
            override fun onItemClick(item: ProductEntity, position: Int) {

            }
        }
    }

    private fun setUpRecyclerView() {
        val spannedGridLayoutManager = StaggeredGridLayoutManager(
            spanCount,
            StaggeredGridLayoutManager.VERTICAL
        )
        spannedGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = productPagerAdapter
                .withLoadStateHeaderAndFooter(
                    header = ProductLoadStateAdapter {
                        productPagerAdapter.retry()
                    },
                    footer = ProductLoadStateAdapter {
                        productPagerAdapter.retry()
                    }
                )
            addItemDecoration(SpacesItemDecoration(8))
        }
    }

    //This handle the motion layout view visibility
    private fun enableView(view: View, viewId: Int, enabled: Boolean) {
        val visibility = if (enabled) View.VISIBLE else View.GONE
        binding.motionLayout.getConstraintSet(viewId)?.let {
            it.setVisibility(viewId, visibility)
            view.requestLayout() // <-- this line
        }

        startSet.setVisibility(viewId, visibility)
        endSet.setVisibility(viewId, visibility)
    }
}
