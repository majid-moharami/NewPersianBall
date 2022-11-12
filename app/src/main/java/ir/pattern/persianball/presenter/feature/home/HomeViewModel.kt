package ir.pattern.persianball.presenter.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.home.Course
import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.home.Slide
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.presenter.feature.home.recycler.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()

    init {
        if (_recyclerItems.value == null) {
            val l = listOf(Course("1"),Course("1"),Course("1"),Course("1"),Course("1"))
            val list_course = mutableListOf<RecyclerItem>()
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            list_course.add(RecyclerItem(HomeCourseData(Course("1"))))
            val co = RecyclerData(flowOf(PagingData.from(list_course)))
            val list = mutableListOf<RecyclerItem>()
            list.add(RecyclerItem(HomeSliderData(Slide("slier"))))
            list.add(RecyclerItem(HomeCoursesRowData(Courses(l), co)))
            list.add(RecyclerItem(HomeRecyclerHeaderData()))
            list.add(RecyclerItem(HomeProductData(Product("1"))))
            list.add(RecyclerItem(HomeProductData(Product("2"))))
            list.add(RecyclerItem(HomeProductData(Product("3"))))
            list.add(RecyclerItem(HomeProductData(Product("4"))))
            list.add(RecyclerItem(HomeProductData(Product("5"))))
            list.add(RecyclerItem(HomeProductData(Product("6"))))
            list.add(RecyclerItem(HomeProductData(Product("7"))))
            list.add(RecyclerItem(HomeProductData(Product("8"))))
            list.add(RecyclerItem(HomeProductData(Product("9"))))
            list.add(RecyclerItem(HomeProductData(Product("10"))))
            list.add(RecyclerItem(HomeProductData(Product("11"))))
            list.add(RecyclerItem(HomeProductData(Product("12"))))
            list.add(RecyclerItem(HomeProductData(Product("13"))))
            list.add(RecyclerItem(HomeProductData(Product("14"))))
            list.add(RecyclerItem(HomeProductData(Product("15"))))
            list.add(RecyclerItem(HomeProductData(Product("16"))))
            list.add(RecyclerItem(HomeProductData(Product("17"))))
            list.add(RecyclerItem(HomeProductData(Product("18"))))
            _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
        }
    }
}