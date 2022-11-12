package ir.pattern.persianball.presenter.feature.home.recycler

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.*
import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeCoursesHorizontalDataAdapter() : PagingHorizontalDataAdapter(){
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            HomeCourseData.VIEW_TYPE -> HomeCourseViewHolder(view)
            else -> null
        }
    }
}
open class HomeCoursesHorizontalViewHolder(itemView: View) : NestedRecyclerViewHolder<HomeCoursesRowData>(itemView){

    val dataAdapter: HomeCoursesHorizontalDataAdapter get() = pagingAdapter as HomeCoursesHorizontalDataAdapter
    override fun getRecyclerAdapter(maxSpan: Int): PagingHorizontalDataAdapter {
        return HomeCoursesHorizontalDataAdapter()
    }

    override fun getSnapHelperType(): Int {
        TODO("Not yet implemented")
    }
}



class HomeCourseHorizontalViewHolder(itemView: View) : HomeCoursesHorizontalViewHolder(itemView) {

//    init {
//        activityComponent().inject(this@MovieHomeMovieLargeHorizontalViewHolder)
//    }

    override fun getVisibleColumn(data: HomeCoursesRowData): Float {
        val outValue = TypedValue()

        return outValue.float
    }
}



class HomeCoursesRowData(var courses: Courses, homeCourseFlow: RecyclerData) :
    NestedRecyclerData(homeCourseFlow), Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_home_new_course_horizontal
    }
    override val viewType: Int= VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
        var result = courses.hashCode()
        result = 31 * result + viewType
        return result
    }

}