package ir.pattern.persianball.presenter.feature.store.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.store.StoreDto
import ir.pattern.persianball.databinding.HolderStoreProductBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseViewHolder

class StoreData(val storeDto: StoreDto) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_store_product
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int = javaClass.hashCode()
}

class StoreViewHolder(
    itemView: View,
    private val onProductPageClickListener: OnClickListener<StoreViewHolder, StoreData>?,
    private val onShoppingCartClickListener: OnClickListener<StoreViewHolder, StoreData>?
) : BaseViewHolder<StoreData>(itemView) {

    lateinit var binding: HolderStoreProductBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderStoreProductBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: StoreData?) {
        data?.storeDto?.also {
            if (it.isAcademy) {
                it.academyDto?.also { academy ->
                    binding.name.text = academy.courseTitle
                    Glide.with(itemView).load("https://api.persianball.ir/${academy.image}")
                        .into(binding.picture)
//                    binding.realPrice.text = itemView.resources.getString(R.string.product_price, academy.price)
                    binding.percent.isVisible = false
                }
            } else {
                it.product?.also { product ->
                    binding.name.text = product.nameFarsi
                    Glide.with(itemView).load(product.image).into(binding.picture)
                    binding.realPrice.text =
                        itemView.resources.getString(R.string.product_price, product.price)
                    product.price?.also { price ->
                        if (product.discountPercentage != null) {
                            if (product.discountPercentage > 0) {
                                binding.percent.isVisible = true
                                binding.discountPercent.text = "%${product.discountPercentage}"
                            }
                            binding.discountedPrice.text = itemView.resources.getString(
                                R.string.product_price,
                                (price - (price * product.discountPercentage / 100))
                            )
                        }
                    }
                }
            }
        }
//        setOnClickListener(binding.productDetailCardView, onProductPageClickListener, this, data)
//        setOnClickListener(binding.addProductCardView, onShoppingCartClickListener, this, data)
    }
}