package ir.pattern.persianball.presenter.feature.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.model.store.StoreDto
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.home.recycler.HomeCourseData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeCoursesRowData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeProductData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeRecyclerHeaderData
import ir.pattern.persianball.presenter.feature.store.recycler.FilterData
import ir.pattern.persianball.presenter.feature.store.recycler.SearchData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import kotlinx.coroutines.flow.*
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
                    it.data.result.map { course ->
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
                    it.data.products.map { product ->
                        recyclerList.add(
                            RecyclerItem(StoreData(StoreDto(false, product = product)))
                        )
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
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

    suspend fun addCartItem(item: CartItem){
        shoppingCartRepository.addCartItem(item)
    }
}