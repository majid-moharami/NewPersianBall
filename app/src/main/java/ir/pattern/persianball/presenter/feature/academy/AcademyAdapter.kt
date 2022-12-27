package ir.pattern.persianball.presenter.feature.academy

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseViewHolder

class AcademyAdapter : BasePagingAdapter() {
    lateinit var onCourseClickListener: BaseViewHolder.OnClickListener<AcademyCourseViewHolder, AcademyCourseData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            AcademyCourseData.VIEW_TYPE -> AcademyCourseViewHolder(view, onCourseClickListener)
            else -> null
        }
    }

}