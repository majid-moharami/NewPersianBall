package ir.pattern.persianball.presenter.feature.setting.progress

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.setting.progress.recycler.ProgressData
import ir.pattern.persianball.presenter.feature.setting.progress.recycler.ProgressViewHolder

class ProgressAdapter: BasePagingAdapter() {
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            ProgressData.VIEW_TYPE -> ProgressViewHolder(view)
            else -> null
        }
    }
}