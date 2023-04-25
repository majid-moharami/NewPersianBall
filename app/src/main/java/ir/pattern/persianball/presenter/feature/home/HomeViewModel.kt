package ir.pattern.persianball.presenter.feature.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.home.Course
import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.home.Slide
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCart
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.home.recycler.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    private val recyclerList = mutableListOf<RecyclerItem>()
    private val _cartList = MutableSharedFlow<Resource<Any?>>()
    val cartList = _cartList.asSharedFlow()
    suspend fun getGallery() {
        _cartList.emit(Resource.Loading())
        homeRepository.getGallery().collect {
            when (it) {
                is Resource.Success -> {
                    recyclerList.add(RecyclerItem(HomeSliderData(it.data)))
                    viewModelScope.launch {
                        getCourses()
                    }
                }
                is Resource.Failure -> {
                    _cartList.emit(Resource.Failure(it.error))
                }
                else -> {}
            }
        }
    }

    private suspend fun getCourses() {
        homeRepository.getCourses().collect {
            when (it) {
                is Resource.Success -> {
                    val listCourse = mutableListOf<RecyclerItem>()
                    it.data.result.map { course ->
                        listCourse.add(RecyclerItem(HomeCourseData(course)))
                    }
                    val courses = RecyclerData(flowOf(PagingData.from(listCourse)))
                    recyclerList.add(RecyclerItem(HomeCoursesRowData(courses)))
                    viewModelScope.launch {
                        getProducts()
                    }
                }
                is Resource.Failure -> {
                    _cartList.emit(Resource.Failure(it.error))
                }
                else -> {}
            }
        }
    }

    private suspend fun getProducts() {
        homeRepository.getProducts().collect {
            when (it) {
                is Resource.Success -> {
                    homeRepository.products = it.data
                    _cartList.emit(it)
                    recyclerList.add(RecyclerItem(HomeRecyclerHeaderData("محصولات جدید")))
                    it.data.products.map { product ->
                        recyclerList.add(RecyclerItem(HomeProductData(product)))
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
                }
                is Resource.Failure -> {
                    _cartList.emit(Resource.Failure(it.error))
                }
                else -> {}
            }
        }
    }

    init {
        if (_recyclerItems.value == null) {
            viewModelScope.launch {
                getGallery()
            }
        }
    }
}