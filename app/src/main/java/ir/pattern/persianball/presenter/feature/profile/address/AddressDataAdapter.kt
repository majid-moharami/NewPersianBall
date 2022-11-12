package ir.pattern.persianball.presenter.feature.profile.address

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemViewHolder

class AddressDataAdapter() : BasePagingAdapter() {
    lateinit var onEditClickListener: BaseViewHolder.OnClickListener<InfoItemViewHolder, InfoItemData>
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            InfoItemData.VIEW_TYPE -> InfoItemViewHolder(view, onEditClickListener)
            else -> null
        }
    }

}