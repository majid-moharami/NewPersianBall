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
import ir.pattern.persianball.data.model.shoppingCart.DeliveryType
import ir.pattern.persianball.data.model.shoppingCart.Discount
import ir.pattern.persianball.data.model.shoppingCart.Order
import ir.pattern.persianball.databinding.FragmentPaymentBinding
import ir.pattern.persianball.presenter.feature.shopping.OrderRecordActivity
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    lateinit var binding: FragmentPaymentBinding
    private val viewModel: ShoppingCartViewModel by viewModels()
    private val args: PaymentFragmentArgs by navArgs()
    private val decimalForm =
        DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US).apply {
            groupingSeparator = ','
        })

    var totalPrice = 0F
    var natPrice = 0F
    var shipingPrice = 0
    var discount = 0

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
                    if (args.addressId == -1) {
                        viewModel.doOrder(
                            Order(args.deliveryMethod, null, it, true)
                        )
                    } else {
                        viewModel.doOrder(
                            Order(args.deliveryMethod, args.addressId, it, true)
                        )
                    }
                } ?: kotlin.run {
                    if (args.addressId == -1) {
                        viewModel.doOrder(
                            Order(args.deliveryMethod, null, isBrowserView = true)
                        )
                    } else {
                        viewModel.doOrder(
                            Order(args.deliveryMethod, args.addressId, isBrowserView = true)
                        )
                    }
                }
            }
        }

        binding.discount.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val discountCode = binding.discountTxt.text.toString()
                if (discountCode.isNotEmpty()) {
                    viewModel.getDiscount(Discount(discountCode), discountCode)
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
//                        val browserIntent = Intent(activity, PaymentWebViewActivity::class.java)
//                        browserIntent.putExtra("url", it.data?.url)
//                        activity?.startActivityForResult(
//                            browserIntent,
//                            OrderRecordActivity.PAYMENT_REQUEST_CODE
//                        )

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(it.data?.url)
                        }
                        startActivity(intent)
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
                            binding.totalPrice.text = resources.getString(
                                R.string.product_price,
                                decimalForm.format(
                                    totalPrice.toInt()
                                        .minus(totalPrice * percent!! / 100) + natPrice + shipingPrice
                                )
                            )
                            val s = totalPrice.toInt()
                                .minus(totalPrice * percent!! / 100) + natPrice + shipingPrice
                            val b = totalPrice - s
                            binding.discountPrice.text =
                                resources.getString(
                                    R.string.product_price,
                                    decimalForm.format(
                                        b + discount
                                    )
                                )

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
                    if (price.totalPrice.toInt() == 0 && !OrderRecordActivity.isSuccess) {
                        activity?.finish()
                    } else {
                        totalPrice = price.totalPrice
                        natPrice = price.nat
                        discount = price.discount.toInt()
                        if (!price.isFreeShippingPrice) {
                            shipingPrice = when (args.deliveryMethod) {
                                "post" -> price.postShippingPrice
                                "peyk" -> price.peykShippingPrice
                                else -> 0
                            }
                        }

                        binding.totalPrice.text =
                            resources.getString(
                                R.string.product_price,
                                decimalForm.format(price.totalPrice.toInt() + price.nat.toInt() + price.shippingPrice.toInt())
                            )
                        binding.discountPrice.text =
                            resources.getString(
                                R.string.product_price,
                                decimalForm.format(price.discount.toInt())
                            )
                        binding.natPrice.text =
                            resources.getString(
                                R.string.product_price,
                                decimalForm.format(price.nat.toInt())
                            )
                        binding.postPrice.text = resources.getString(
                            R.string.product_price,
                            decimalForm.format(shipingPrice)
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        if (!viewModel.shoppingCartRepository.isShipping) {
            requireActivity().finish()
        }
        super.onDestroy()
    }

}