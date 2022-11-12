package ir.pattern.persianball.presenter.feature.profile.password

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.password.recycler.PasswordItemsData
import ir.pattern.persianball.presenter.feature.profile.password.recycler.PasswordItemsViewHolder
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemViewHolder

class PasswordDataAdapter: BasePagingAdapter() {
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            PasswordItemsData.VIEW_TYPE -> PasswordItemsViewHolder(view)
            else -> null
        }
    }
}