package ir.pattern.persianball.presenter.feature.profile

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.recycler.*

class ProfileDataAdapter(private val fm: FragmentManager, val lifecycle: Lifecycle) : BasePagingAdapter() {
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            ProfileImageData.VIEW_TYPE -> ProfileImageViewHolder(view)
            ProfileNameData.VIEW_TYPE -> ProfileNameViewHolder(view)
            ProfileInformationData.VIEW_TYPE -> ProfileInformationViewHolder(view, fm, lifecycle)
            else -> null
        }
    }
}