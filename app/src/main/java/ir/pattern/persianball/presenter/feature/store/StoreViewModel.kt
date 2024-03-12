package ir.pattern.persianball.presenter.feature.store

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.AcademyHomeDto
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.store.StoreDto
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.store.recycler.FilterData
import ir.pattern.persianball.presenter.feature.store.recycler.SearchData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel
@Inject constructor(
    private val homeRepository: HomeRepository,
    private val shoppingCartRepository: ShoppingCartRepository
) : BaseViewModel() {
    private val recyclerList = mutableListOf<RecyclerItem>()
    private val _cartList = MutableSharedFlow<Resource<Any?>>()
    val cartList = _cartList.asSharedFlow()

    private lateinit var courseList: List<AcademyHomeDto>
    private lateinit var productList: List<Product>

    var isClassFilter = false
    var isProductFilter = false
    var isCourseFilter = false

    init {
        viewModelScope.launch {
            getCourses()
        }
    }

    suspend fun getCourses() {
        _cartList.emit(Resource.Loading())
        homeRepository.getCourses().collect {
            when (it) {
                is Resource.Success -> {
                    RecyclerItem(SearchData())
                    RecyclerItem(FilterData())
                    courseList = it.data.result
                    it.data.result.sortedBy { course -> course.sortingOrder }.map { course ->
                        recyclerList.add(
                            RecyclerItem(StoreData(StoreDto(true, academyDto = course)))
                        )
                    }
                    getProducts()
                }

                is Resource.Failure -> {
                    _cartList.emit(Resource.Failure(it.error))
                }

                else -> {
                    _cartList.emit(Resource.Loading())
                }
            }
        }
    }

    private suspend fun getProducts() {
        homeRepository.getProducts().collect {
            when (it) {
                is Resource.Success -> {
                    _cartList.emit(it)
                    productList = it.data.products
                    it.data.products.sortedBy { p -> p.sortingOrder }.map { product ->
                        recyclerList.add(
                            RecyclerItem(StoreData(StoreDto(false, product = product)))
                        )
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(sortList(recyclerList))))
                }

                is Resource.Failure -> {
                    _cartList.emit(Resource.Failure(it.error))
                }

                else -> {
                    _cartList.emit(Resource.Loading())
                }
            }
        }
    }

    fun sortList(recyclerItems: List<RecyclerItem>): List<RecyclerItem> {
        return recyclerItems.sortedBy { if ((it.data as StoreData).storeDto.product?.sortingOrder != null) (it.data as StoreData).storeDto.product?.sortingOrder else (it.data as StoreData).storeDto.academyDto?.sortingOrder }
    }

    fun filterProducts() {
        val filteredList = mutableListOf<RecyclerItem>()

        if (!isCourseFilter && !isClassFilter && !isProductFilter) {
            _recyclerItems.value = RecyclerData(flowOf(PagingData.from(sortList(recyclerList))))
            return
        }
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(sortList(filteredList))))
        if (isCourseFilter) {
            courseList.map {
                if (it.category?.nameFarsi == "دوره ها") {
                    filteredList.add(RecyclerItem(StoreData(StoreDto(true, academyDto = it))))
                }
            }
        }
        if (isClassFilter) {
            courseList.map {
                if (it.category?.nameFarsi == "کلاس ها") {
                    filteredList.add(RecyclerItem(StoreData(StoreDto(true, academyDto = it))))
                }
            }
        }
        if (isProductFilter) {
            productList.map {
                filteredList.add(RecyclerItem(StoreData(StoreDto(false, product = it))))
            }
        }
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(sortList(filteredList))))
    }

    fun search(query: String?) {
        val searchedList = mutableListOf<RecyclerItem>()
        if (!query.isNullOrEmpty()) {
            courseList.map { course ->
                if (course.nameFarsi.contains(query, true)) {
                    searchedList.add(RecyclerItem(StoreData(StoreDto(true, academyDto = course))))
                }
            }
            productList.map { product ->
                if (product.nameFarsi.contains(query, true)) {
                    searchedList.add(RecyclerItem(StoreData(StoreDto(false, product = product))))
                }
            }
        } else {
            courseList.map {
                searchedList.add(RecyclerItem(StoreData(StoreDto(true, academyDto = it))))
            }
            productList.map { product ->
                searchedList.add(RecyclerItem(StoreData(StoreDto(false, product = product))))
            }
        }
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(sortList(searchedList))))
    }
}