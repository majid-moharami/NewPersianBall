package ir.pattern.persianball.presenter.feature.shopping

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentShoppingCartListBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShoppingCartListFragment : Fragment() {

    lateinit var binding: FragmentShoppingCartListBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_shopping_cart_list,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.shopCart.collectLatest {
                it.price.also { price ->
                    binding.totalPrice.text =
                        resources.getString(R.string.product_price, price.totalPrice.toInt())
                    binding.discountPrice.text =
                        resources.getString(R.string.product_price, price.discount.toInt())
                    binding.natPrice.text =
                        resources.getString(R.string.product_price, price.nat.toInt())
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
                            recyclerData.shoppingCartItemDto.quantity + 1,
                            recyclerData.shoppingCartItemDto.id
                        )
                    }
                }

            it.onMinusCartItemClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        if (recyclerData.shoppingCartItemDto.quantity > 1) {
                            viewModel.updateItemQuantity(
                                recyclerData.shoppingCartItemDto.quantity + 1,
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
            viewModel.recyclerItems.collectLatest {

                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
    }
}