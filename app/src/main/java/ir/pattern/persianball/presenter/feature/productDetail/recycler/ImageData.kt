package ir.pattern.persianball.presenter.feature.productDetail.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderProductImagesBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class ImageData (val imageUrl : String, val isVideo: Boolean = false, val videoUrl : String? =  null) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_product_images
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class ImageViewHolder(
    itemView: View,
    private val onImageClickListener: OnClickListener<ImageViewHolder, ImageData>?
) : BaseViewHolder<ImageData>(itemView) {
    private lateinit var binding: HolderProductImagesBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderProductImagesBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: ImageData) {
        Glide.with(itemView.context).load("https://api.persianball.ir/${data.imageUrl}")
            .into(binding.image)
        if (data.isVideo){
            binding.icPlay.isVisible = true
        }
        setOnClickListener(binding.image, onImageClickListener, this, data)
    }

}