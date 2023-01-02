package ir.pattern.persianball.presenter.feature.setting.registered

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseViewHolder
import ir.pattern.persianball.presenter.feature.setting.registered.recycler.RegisteredCoursesData
import ir.pattern.persianball.presenter.feature.setting.registered.recycler.RegisteredCoursesViewHolder

class RegisteredCoursesAdapter: BasePagingAdapter() {
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            RegisteredCoursesData.VIEW_TYPE -> RegisteredCoursesViewHolder(view)
            else -> null
        }
    }
}