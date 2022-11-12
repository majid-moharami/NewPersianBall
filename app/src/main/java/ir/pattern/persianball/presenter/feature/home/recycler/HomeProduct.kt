package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.databinding.HolderHomeSliderBinding
import ir.pattern.persianball.databinding.HomeNewProductViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeProductData(var product: Product) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.home_new_product_viewholder
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hashCode(): Int {
        return product.hashCode()
    }
}

class HomeProductViewHolder(itemView: View) : BaseViewHolder<HomeProductData>(itemView) {
    private lateinit var binding: HomeNewProductViewholderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HomeNewProductViewholderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: HomeProductData?) {
        binding.title.text = "جا توپی"
        binding.realPrice.text = "40000 تومان"
        binding.discountedPrice.text = "30000 تومان"
        binding.shapeableImageView.setImageDrawable(itemView.resources.getDrawable(R.drawable.real_ball))
    }

}