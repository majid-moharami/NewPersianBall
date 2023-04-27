package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.databinding.HolderHomeSliderBinding
import ir.pattern.persianball.databinding.HomeNewProductViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class HomeProductData(var product: Product) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.home_new_product_viewholder
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class HomeProductViewHolder(
    itemView: View,
    private val onProductClickListener: OnClickListener<HomeProductViewHolder, HomeProductData>?
) : BaseViewHolder<HomeProductData>(itemView) {
    private lateinit var binding: HomeNewProductViewholderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HomeNewProductViewholderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: HomeProductData) {
        data.product.also {
            binding.title.text = it.nameFarsi
            binding.realPrice.text = itemView.resources.getString(R.string.product_price, it.price)
            if (it.discountPercentage != null) {
                binding.discountedPrice.text = itemView.resources.getString(
                    R.string.product_price,
                    (it.price?.minus((it.price * it.discountPercentage / 100)))
                )
            }
            Glide.with(itemView.context).load("https://api.persianball.ir/${it.image}").into(binding.shapeableImageView)
        }
        setOnClickListener(binding.clickableLayout, onProductClickListener, this, data)
    }

}