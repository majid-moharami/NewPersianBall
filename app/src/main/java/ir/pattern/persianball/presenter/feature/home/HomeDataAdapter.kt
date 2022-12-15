package ir.pattern.persianball.presenter.feature.home

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.home.recycler.*

class HomeDataAdapter : BasePagingAdapter() {

    lateinit var onCourseClickListener: BaseViewHolder.OnClickListener<HomeCourseViewHolder, HomeCourseData>
    lateinit var onProductClickListener: BaseViewHolder.OnClickListener<HomeProductViewHolder, HomeProductData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            HomeCoursesRowData.VIEW_TYPE -> HomeCourseHorizontalViewHolder(
                view,
                onCourseClickListener
            )
            HomeCourseData.VIEW_TYPE -> HomeCourseViewHolder(view, onCourseClickListener)
            HomeProductData.VIEW_TYPE -> HomeProductViewHolder(view, onProductClickListener)
            HomeRecyclerHeaderData.VIEW_TYPE -> HomeRecyclerHeaderViewHolder(view)
            HomeSliderData.VIEW_TYPE -> HomeSliderViewHolder(view)
            else -> null
        }
    }
}