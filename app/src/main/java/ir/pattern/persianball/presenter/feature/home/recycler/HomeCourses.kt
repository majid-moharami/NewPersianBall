package ir.pattern.persianball.presenter.feature.home.recycler

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.*
import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeCoursesHorizontalDataAdapter() : PagingHorizontalDataAdapter() {

    lateinit var onCourseClickListener: BaseViewHolder.OnClickListener<HomeCourseViewHolder, HomeCourseData>

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            HomeCourseData.VIEW_TYPE -> HomeCourseViewHolder(view, onCourseClickListener)
            else -> null
        }
    }
}

open class HomeCoursesHorizontalViewHolder(
    itemView: View,
    private val onCourseClickListener: OnClickListener<HomeCourseViewHolder, HomeCourseData>
) :
    NestedRecyclerViewHolder<HomeCoursesRowData>(itemView) {

    val dataAdapter: HomeCoursesHorizontalDataAdapter get() = pagingAdapter as HomeCoursesHorizontalDataAdapter
    override fun getRecyclerAdapter(maxSpan: Int): PagingHorizontalDataAdapter {
        return HomeCoursesHorizontalDataAdapter()
    }

    override fun onAttach(data: HomeCoursesRowData) {
        super.onAttach(data)
        dataAdapter.onCourseClickListener = OnClickListener { view, viewHolder, recyclerData ->
            onCourseClickListener.onClick(view, viewHolder, recyclerData)
        }
    }

    override fun getSnapHelperType(): Int {
        TODO("Not yet implemented")
    }
}


class HomeCourseHorizontalViewHolder(
    itemView: View,
    onCourseClickListener: OnClickListener<HomeCourseViewHolder, HomeCourseData>
) : HomeCoursesHorizontalViewHolder(itemView, onCourseClickListener) {

//    init {
//        activityComponent().inject(this@MovieHomeMovieLargeHorizontalViewHolder)
//    }

    override fun getVisibleColumn(data: HomeCoursesRowData): Float {
        val outValue = TypedValue()

        return outValue.float
    }
}


class HomeCoursesRowData(private val homeCourses: RecyclerData) :
    NestedRecyclerData(homeCourses), Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_home_new_course_horizontal
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
//        var result = courses.hashCode()
//        result = 31 * result + viewType
        return 0
    }

}