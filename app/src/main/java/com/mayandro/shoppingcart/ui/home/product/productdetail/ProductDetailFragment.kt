package com.mayandro.shoppingcart.ui.home.product.productdetail

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.FragmentProductDetailBinding
import com.mayandro.shoppingcart.ui.base.BaseFragment
import com.mayandro.shoppingcart.ui.home.product.productdetail.adapter.ColorAdapter
import com.mayandro.shoppingcart.ui.home.product.productdetail.adapter.SizeAdapter
import com.mayandro.utility.extensions.setImage
import com.mayandro.utility.extensions.strike
import com.mayandro.utility.network.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailFragment: BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>(), ProductDetailInteractor {

    private val colorAdapter: ColorAdapter by lazy { ColorAdapter() }
    private val sizeAdapter: SizeAdapter by lazy { SizeAdapter() }

    private var isPortraitMode: Boolean = true

    override fun getViewModelClass() = ProductDetailViewModel::class.java

    override fun getViewBinding() = FragmentProductDetailBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isPortraitMode = (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewInteractor = this

        val productId = arguments?.getInt("productId") ?: return

        if(savedInstanceState == null) viewModel.getProductDetail(productId)

        setColorList()
        setSizeList()
        setTopNavigationButton()
        setCartButton()

        viewModel.productDetailLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkStatus.Success -> {
                    binding.textViewError.isVisible = false
                    binding.motionLayout.isVisible = true
                    binding.progressBar.isVisible = false
                    setProductData(it.data)
                }
                is NetworkStatus.Error -> {
                    showError(it.errorMessage?: "Unexpected error happened.")
                    binding.textViewError.isVisible = true
                    binding.motionLayout.isVisible = false
                    binding.progressBar.isVisible = false
                }
                is NetworkStatus.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.motionLayout.isVisible = false
                    binding.textViewError.isVisible = false
                }
            }
        }
    }

    private fun showError(message: String) {
        binding.textViewError.text = message
        showErrorDialog(message)
    }

    private fun setCartButton() {
        binding.buttonCart.setOnClickListener {
            showDialogMessage(
                title = getString(R.string.coming_soon),
                message = getString(R.string.coming_soon_message)
            )
        }
    }

    private fun setTopNavigationButton() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setSizeList() {
        lifecycleScope.launch {
            flow {
                emit(viewModel.getSizeList())
            }.flowOn(Dispatchers.IO)
                .collect {
                    sizeAdapter.dataSet = it
                }
        }

        binding.sizeList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = sizeAdapter
        }
    }

    private fun setColorList() {
        lifecycleScope.launch {
            flow {
                emit(viewModel.getColorList())
            }.flowOn(Dispatchers.IO)
                .collect {
                colorAdapter.dataSet = it
            }
        }

        binding.colorList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext(),
                if(isPortraitMode) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
                false
            )
            adapter = colorAdapter
        }
    }

    private fun setProductData(productDetail: ProductDetail?) {
        if(productDetail == null) {
            findNavController().popBackStack()
            return
        }

        binding.productName.text = productDetail.brand
        binding.productPrice.text = "${productDetail.price} ${productDetail.currency}"
        binding.productDiscount.visibility = View.GONE
        binding.productDescription.text = productDetail.description

        if(productDetail.stock <= 0) {
            binding.textButtonCart.text = getString(R.string.notify_me_when_available)
            binding.buttonCart.isClickable = false
            binding.buttonCart.isEnabled = false
            binding.buttonCart.isFocusable = false
            binding.buttonCart.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.textColorGrey
                )
            )
        }

        if(productDetail.discountPercentage > 30) {
            enableSaleImage(true)
        }

        if(productDetail.discountPercentage > 0) {
            lifecycleScope.launch {
                flow {
                    emit(productDetail.price + (productDetail.price * productDetail.discountPercentage) * 0.01)
                }.collect {
                    binding.productDiscount.text = "$it ${productDetail.currency}"
                    binding.productDiscount.strike = true
                }
            }
        }
        binding.imageProduct.setImage(
            productDetail.image,
            requestOptions = RequestOptions()
        )
    }

    private fun enableSaleImage(enabled: Boolean) {
        val visibility = if (enabled) View.VISIBLE else View.GONE
        val startSet: ConstraintSet = binding.motionLayout.getConstraintSet(R.layout.fragment_product_detail)
        startSet.setVisibility(R.id.imageSale, visibility)

        val endSet: ConstraintSet = binding.motionLayout.getConstraintSet(R.layout.fragment_product_detail_end)
        endSet.setVisibility(R.id.imageSale, visibility)
    }

    private fun showErrorDialog(string: String) {
        showDialogMessage(
            title = "Something went wrong",
            message = string,
            positiveButton = "Close",
            positiveButtonClickListener = {
                findNavController().popBackStack()
            }
        )
    }

    override fun onDestroyView() {
        clearDialogMessage()
        super.onDestroyView()
    }
}