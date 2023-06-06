package ir.pattern.persianball.presenter.feature.productDetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.databinding.FragmentProductDetailBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import ir.pattern.persianball.presenter.feature.player.PlayerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class ProductDetailFragment : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()
    var product: Product? = null
    private var productId = -1
    private lateinit var args: ProductDetailFragmentArgs
    private var currentSizeIndex = 0
    private var currentColorIndex = 0
    private var productCount = 1
    var pagingAdapter: BasePagingAdapter? = null
    private val decimalForm =
        DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US).apply {
            groupingSeparator = ','
        })

    @Inject
    lateinit var playerRepository: PlayerRepository

    @Inject
    lateinit var accountManager: AccountManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = ProductDetailFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productId = args.id
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
        product = viewModel.getProductById(productId)
        initView()
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.plus.setOnClickListener {
            productCount += 1
            binding.productCount.text = productCount.toString()
        }
        binding.minus.setOnClickListener {
            if (productCount <= 1) return@setOnClickListener
            productCount -= 1
            binding.productCount.text = productCount.toString()
        }
        binding.addProduct.setOnClickListener {
            binding.addProduct.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                if (!accountManager.isLogin) {
                    Toast.makeText(
                        requireActivity(),
                        "برای اضافه کردن به سبد خرید ابتدا لاگین کنید.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }
                product?.also {
                    if (it.variants.isNullOrEmpty()) {
                        viewModel.addCartItem(
                            CartItem(
                                product = it.id,
                                quantity = binding.productCount.text.toString().toInt()
                            )
                        )
                    } else {
                        viewModel.addCartItem(
                            CartItem(
                                product = viewModel.getSelectedVariantId(
                                    it,
                                    currentSizeIndex,
                                    currentColorIndex
                                ),
                                quantity = binding.productCount.text.toString().toInt()
                            )
                        )
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addingCartFlow.collect {
                when (it) {
                    "در حال اضافه کردن به سبد خرید." -> {
                        binding.addProduct.isEnabled = false
                    }

                    else -> {
                        lifecycleScope.launch {
                            delay(4000)
                            binding.addProduct.isEnabled = true
                        }
                    }
                }
                Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.sizeBox.setOnClickListener {

        }

        binding.colorBox.setOnClickListener {

        }

        return binding.root
    }


    private fun initView() {
        binding.productCount.text = productCount.toString()
        with(binding) {
            product?.also {
                Glide.with(requireContext()).load("https://api.persianball.ir/${it.image}")
                    .into(poster)
                headerTitle.text = it.nameFarsi
                description.text = it.description
                description.movementMethod = ScrollingMovementMethod()
                realPrice.text =
                    resources.getString(R.string.product_price, decimalForm.format(it.price))
                it.price?.also { price ->
                    val s = it.discountPercentage
                    if (s != null) {
                        discountedPrice.text = resources.getString(
                            R.string.product_price,
                            (decimalForm.format(price.minus((price * s / 100))))
                        )
                    }
                }

                sizeSpinner.onItemSelectedListener = this@ProductDetailFragment
                colorSpinner.onItemSelectedListener = this@ProductDetailFragment

                it.variants?.also { variants ->
                    if (viewModel.getSizeList(it) == null) {
                        binding.sizeLayout.isVisible = false
                    }
                    if (viewModel.getColors(it) == null) {
                        binding.colorLayout.isVisible = false
                    }

                    if (!binding.sizeLayout.isVisible && !binding.colorLayout.isVisible) {
                        viewModel.anyOne = true
                    }
                    if (binding.sizeLayout.isVisible && binding.colorLayout.isVisible) {
                        viewModel.both = true
                    }
                    if (binding.sizeLayout.isVisible && !binding.colorLayout.isVisible) {
                        viewModel.onlySize = true
                    }
                    if (!binding.sizeLayout.isVisible && binding.colorLayout.isVisible) {
                        viewModel.onlyColor = true
                    }


                    if (viewModel.onlySize == true || viewModel.both == true) {
                        ArrayAdapter(
                            requireActivity(),
                            R.layout.spiner_drop_down,
                            viewModel.getSizeList(it)!!.toList()
                        ).also { adapter ->
                            // Specify the layout to use when the list of choices appears
                            adapter.setDropDownViewResource(R.layout.sppiner_item)
                            // Apply the adapter to the spinner
                            sizeSpinner.adapter = adapter
                        }
                    }

                    if (viewModel.both == true) {
                        ArrayAdapter(
                            requireActivity(),
                            R.layout.spiner_drop_down,
                            viewModel.getColorOFSize(
                                viewModel.getSizeList(it)!!.toList()[0],
                                it
                            )!!.keys.toList()
                        ).also { adapter ->
                            // Specify the layout to use when the list of choices appears
                            adapter.setDropDownViewResource(R.layout.sppiner_item)
                            // Apply the adapter to the spinner
                            colorSpinner.adapter = adapter
                        }
                    }

                    if (viewModel.onlyColor == true) {
                        ArrayAdapter(
                            requireActivity(),
                            R.layout.spiner_drop_down,
                            viewModel.getColors(
                                it
                            )!!.keys.toList()
                        ).also { adapter ->
                            // Specify the layout to use when the list of choices appears
                            adapter.setDropDownViewResource(R.layout.sppiner_item)
                            // Apply the adapter to the spinner
                            colorSpinner.adapter = adapter
                        }
                    }
                }
            }
        }
        setRecycler()
    }

    private fun setRecycler() {
        if (product?.images?.isNotEmpty() == true || product?.video?.isNotEmpty() == true) {
            binding.recyclerView.isVisible = true
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            pagingAdapter = ProductDetailDataAdapter().also {
                binding.recyclerView.adapter = it
                it.onImageClickListener =
                    BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                        if (!recyclerData.isVideo) {
                            Glide.with(requireContext())
                                .load("https://api.persianball.ir/${recyclerData.imageUrl}")
                                .into(binding.poster)
                        } else {
                            playerRepository.videoUrl = null
                            playerRepository.videoUrl = recyclerData.videoUrl
                            val intent = Intent(requireActivity(), PlayerActivity::class.java)
                            intent.putExtra("HAVE_URL", false)
                            intent.putExtra("URL", "")
                            startActivity(intent)
                        }
                    }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                product?.also {
                    pagingAdapter?.submitData(
                        RecyclerData(
                            flowOf(
                                PagingData.from(
                                    viewModel.getImageList(
                                        it
                                    )
                                )
                            )
                        )
                    )
                }
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.parent == binding.sizeBox) {
            handleSizeClick(p2)
        } else {
            handleColorClick(p2)
        }
    }

    private fun handleColorClick(p2: Int) {
        if (viewModel.both == true || viewModel.onlyColor == true) {
            currentColorIndex = p2
            val color = viewModel.currentColorMap[viewModel.currentColorMap.keys.toList()[p2]]
            binding.colorSelected.setCardBackgroundColor(Color.parseColor("#$color"))
            product?.also {
                val variant = viewModel.getSelectedVariant(it, currentSizeIndex, currentColorIndex)
                binding.realPrice.text =
                    resources.getString(R.string.product_price, decimalForm.format(variant?.price))
                variant?.price?.also { price ->
                    val s = variant.discountPercentage
                    binding.discountedPrice.text = resources.getString(
                        R.string.product_price,
                        (decimalForm.format(price.minus((price * s / 100))))
                    )
                }
                viewModel.getSelectedVariantImage(it, currentSizeIndex, currentColorIndex)
                    ?.also { img ->
                        Glide.with(requireContext())
                            .load("https://api.persianball.ir/${img}")
                            .into(binding.poster)
                    }
            }
        }
    }

    private fun handleSizeClick(p2: Int) {
        if (viewModel.both == true) {
            currentSizeIndex = p2
            product?.also {
                ArrayAdapter(
                    requireActivity(),
                    R.layout.spiner_drop_down,
                    viewModel.getColorOFSize(
                        viewModel.getSizeList(it)!!.toList()[p2],
                        it
                    )!!.keys.toList()
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(R.layout.sppiner_item)
                    // Apply the adapter to the spinner
                    binding.colorSpinner.adapter = adapter
                }
            }
            currentColorIndex = 0
            val firstColor = viewModel.currentColorMap[viewModel.currentColorMap.keys.toList()[0]]
            if (firstColor != null) {
                binding.colorSelected.setCardBackgroundColor(Color.parseColor("#$firstColor"))
            }
            product?.also {
                val variant = viewModel.getSelectedVariant(it, currentSizeIndex, currentColorIndex)
                binding.realPrice.text =
                    resources.getString(R.string.product_price, decimalForm.format(variant?.price))
                variant?.price?.also { price ->
                    val s = variant.discountPercentage
                    binding.discountedPrice.text = resources.getString(
                        R.string.product_price,
                        (decimalForm.format(price.minus((price * s / 100))))
                    )
                }
            }
            product?.let {
                viewModel.getSelectedVariantImage(it, currentSizeIndex, currentColorIndex)
                    ?.also { img ->
                        Glide.with(requireContext())
                            .load("https://api.persianball.ir/${img}")
                            .into(binding.poster)
                    }
            }
        } else if (viewModel.onlySize == true) {
            currentSizeIndex = p2
            product?.also {
                val variant = viewModel.getSelectedVariant(it, currentSizeIndex, currentColorIndex)
                binding.realPrice.text =
                    resources.getString(R.string.product_price, decimalForm.format(variant?.price))
                variant?.price?.also { price ->
                    val s = variant.discountPercentage
                    binding.discountedPrice.text = resources.getString(
                        R.string.product_price,
                        (decimalForm.format(price.minus((price * s / 100))))
                    )
                }
            }
            product?.let {
                viewModel.getSelectedVariantImage(it, currentSizeIndex, currentColorIndex)
                    ?.also { img ->
                        Glide.with(requireContext())
                            .load("https://api.persianball.ir/${img}")
                            .into(binding.poster)
                    }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}