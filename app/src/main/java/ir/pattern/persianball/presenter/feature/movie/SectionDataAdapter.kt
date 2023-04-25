package ir.pattern.persianball.presenter.feature.movie

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.home.recycler.*
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionHeaderData
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionHeaderViewHolder
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionItemData
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionItemViewHolder

class SectionDataAdapter : BasePagingAdapter()  {

    lateinit var onSectionHeaderClickListener: BaseViewHolder.OnClickListener<SectionHeaderViewHolder, SectionHeaderData>
    lateinit var onMovieClickListener: BaseViewHolder.OnClickListener<SectionItemViewHolder, SectionItemData>
    lateinit var onSendClickListener: BaseViewHolder.OnClickListener<SectionItemViewHolder, SectionItemData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            SectionHeaderData.VIEW_TYPE -> SectionHeaderViewHolder(view, onSectionHeaderClickListener)
            SectionItemData.VIEW_TYPE -> SectionItemViewHolder(view,onMovieClickListener, onSendClickListener)
            else -> null
        }
    }
}