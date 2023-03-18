package ir.pattern.persianball.presenter.feature.store

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseViewHolder
import ir.pattern.persianball.presenter.feature.store.recycler.*

class StoreAdapter : BasePagingAdapter() {
    lateinit var onProductPageClickListener: BaseViewHolder.OnClickListener<StoreViewHolder, StoreData>
    lateinit var onShoppingCartClickListener: BaseViewHolder.OnClickListener<StoreViewHolder, StoreData>

    lateinit var onSearchClickListener: BaseViewHolder.OnClickListener<SearchViewHolder, SearchData>

    lateinit var onCoursesClickListener: BaseViewHolder.OnClickListener<FilterViewHolder, FilterData>
    lateinit var onProductClickListener: BaseViewHolder.OnClickListener<FilterViewHolder, FilterData>
    lateinit var onClassesClickListener: BaseViewHolder.OnClickListener<FilterViewHolder, FilterData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            StoreData.VIEW_TYPE -> StoreViewHolder(view, onProductPageClickListener, onShoppingCartClickListener)
            FilterData.VIEW_TYPE -> FilterViewHolder(view, onCoursesClickListener, onProductClickListener, onClassesClickListener)
            SearchData.VIEW_TYPE -> SearchViewHolder(view, onSearchClickListener)
            else -> null
        }
    }
}