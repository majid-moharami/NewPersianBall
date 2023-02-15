package ir.pattern.persianball.presenter.feature.shopping.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.shoppingCart.Discount
import ir.pattern.persianball.data.model.shoppingCart.Order
import ir.pattern.persianball.databinding.FragmentPaymentBinding
import ir.pattern.persianball.presenter.feature.login.ChangePasswordFragmentArgs
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    lateinit var binding: FragmentPaymentBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getShoppingCart()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_payment,
            container,
            false
        )
        binding.paymentBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.discountPercent?.also {
                    viewModel.doOrder(
                        Order(args.deliveryMethod, args.addressId, it.toString())
                    )
                } ?: kotlin.run {
                    viewModel.doOrder(
                        Order(args.deliveryMethod, args.addressId)
                    )
                }
            }
        }

        binding.discount.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val discountCode = binding.discountTxt.text.toString()
                if (!discountCode.isNullOrEmpty()) {
                    viewModel.getDiscount(Discount(discountCode))
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.payment.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val browserIntent = Intent(activity, PaymentWebViewActivity::class.java)
                        browserIntent.putExtra("url", it.data?.url)
                        startActivityForResult(browserIntent, 7)
                    }
                    is Resource.Failure -> {
                        it.error.code
                    }
                    else -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.discount.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        it.data?.discountPercent.also { percent ->
                            Toast.makeText(
                                requireContext(),
                                " %$percent تخفیف برای شما اعمال شد.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Failure -> {
                        it.error.code
                    }
                    else -> {}
                }
            }
        }

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
    }


}