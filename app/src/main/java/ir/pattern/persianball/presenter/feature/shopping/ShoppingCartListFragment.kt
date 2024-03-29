package ir.pattern.persianball.presenter.feature.shopping

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.databinding.FragmentShoppingCartListBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


@AndroidEntryPoint
class ShoppingCartListFragment : BaseFragment() {

    lateinit var binding: FragmentShoppingCartListBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    var isFirstTime: Boolean = true
    private val decimalForm =
        DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US).apply {
            groupingSeparator = ','
        })

    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentShoppingCartListBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstTime) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getShoppingCart()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseBinding.empty.isVisible = true
        showLoading(true)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.shopCart.collectLatest {
                it.price.also { price ->
                    binding.totalPrice.text =
                        resources.getString(
                            R.string.product_price,
                            decimalForm.format(price.totalPrice.toInt())
                        )
                    binding.discountPrice.text =
                        resources.getString(
                            R.string.product_price,
                            decimalForm.format(price.discount.toInt())
                        )
                    binding.realTotalPrice.text =
                        resources.getString(
                            R.string.product_price,
                            decimalForm.format(price.discount.toInt() + price.totalPrice.toInt())
                        )
                }
            }
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        pagingAdapter = ShoppingCartAdapter().also {
            binding.recyclerView.adapter = it

            it.onDeleteCartItemClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.deleteCartItem(recyclerData.shoppingCartItemDto.id)
                    }
                }

            it.onAddCartItemClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateItemQuantity(
                            CartItem(null, recyclerData.shoppingCartItemDto.product?.id, recyclerData.shoppingCartItemDto.quantity + 1),
                            recyclerData.shoppingCartItemDto.id
                        )
                    }
                }

            it.onMinusCartItemClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (recyclerData.shoppingCartItemDto.quantity > 1) {
                            viewModel.updateItemQuantity(
                                CartItem(null, recyclerData.shoppingCartItemDto.product?.id, recyclerData.shoppingCartItemDto.quantity - 1),
                                recyclerData.shoppingCartItemDto.id
                            )
                        }
                    }
                }
        }

        binding.continueBtn.setOnClickListener {
            startActivity(Intent(activity, OrderRecordActivity::class.java))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartList.collectLatest {
                    isFirstTime = false
                when (it) {
                    is Resource.Success -> {
                        if (it.data.result[0].items.isEmpty() || it.data.result.isEmpty()) {
                            binding.continueBtn.isEnabled = false
                            showEmptyLayout(true)
                            binding.bottomBar.isVisible = false
                        }
                    }
                    is Resource.Failure -> {

                    }
                    else -> {

                    }
                }
            }
        }

        baseBinding.tryAgainBtn.setOnClickListener {
            showTryAgainView(false)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getShoppingCart()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listRequest.collect {
                when (it) {
                    is Resource.Success -> {
                        baseBinding.empty.isVisible = false
                        showLoading(false)
                        binding.recyclerView.isVisible = true
                        binding.continueBtn.isEnabled = true
                    }
                    is Resource.Failure -> {
                        baseBinding.empty.isVisible = false
                        showLoading(false)
                        showTryAgainView(true)
                        binding.recyclerView.isVisible = false
                    }
                    else -> {
                        baseBinding.empty.isVisible = true
                        showLoading(true)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
    }
}