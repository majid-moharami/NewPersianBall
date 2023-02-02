package ir.pattern.persianball.presenter.feature.productDetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    lateinit var binding: FragmentProductDetailBinding
    lateinit var product: Product
    private lateinit var args: ProductDetailFragmentArgs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = ProductDetailFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = args.product
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_product_detail,
            container,
            false
        )
        initView()
        return binding.root
    }

    private fun initView() {
        with(binding) {
            Glide.with(requireContext()).load("https://api.persianball.ir/${product.image}").into(poster)
            headerTitle.text = product.nameFarsi
            description.text = product.description
            realPrice.text = resources.getString(R.string.product_price, product.price)
            product.price?.also {
                val s = product.discountPercentage
                if (s != null) {
                    discountedPrice.text = resources.getString(
                        R.string.product_price,
                        (it.minus((it * s / 100)))
                    )
                }
            }
        }
    }
}