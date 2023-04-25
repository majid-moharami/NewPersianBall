package ir.pattern.persianball.presenter.feature.movie.dialog

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.home.recycler.*
import ir.pattern.persianball.presenter.feature.movie.dialog.recycler.*

class DialogChooseDataAdapter : BasePagingAdapter() {

    lateinit var onLocationClickListener: BaseViewHolder.OnClickListener<LocationItemViewHolder, LocationItemData>
    lateinit var onCoachClickListener: BaseViewHolder.OnClickListener<CoachItemViewHolder, CoachItemData>
    lateinit var onGiftClickListener: BaseViewHolder.OnClickListener<GiftProductItemViewHolder, GiftProductItemData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            LocationItemData.VIEW_TYPE -> LocationItemViewHolder(
                view,
                onLocationClickListener
            )
            CoachItemData.VIEW_TYPE -> CoachItemViewHolder(view, onCoachClickListener)
            GiftProductItemData.VIEW_TYPE -> GiftProductItemViewHolder(view, onGiftClickListener)
            else -> null
        }
    }
}