package ir.pattern.persianball.presenter.feature.productDetail

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.data.model.base.PagingHorizontalDataAdapter
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.productDetail.recycler.ImageData
import ir.pattern.persianball.presenter.feature.productDetail.recycler.ImageViewHolder

class ProductDetailDataAdapter : BasePagingAdapter() {

    lateinit var onImageClickListener: BaseViewHolder.OnClickListener<ImageViewHolder, ImageData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            ImageData.VIEW_TYPE -> ImageViewHolder(view, onImageClickListener)
            else -> null
        }
    }
}