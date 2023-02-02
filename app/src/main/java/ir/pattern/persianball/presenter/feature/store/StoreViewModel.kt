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
import ir.pattern.persianball.presenter.feature.home.recycler.HomeCourseData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeCoursesRowData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeProductData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeRecyclerHeaderData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel
@Inject constructor(
    private val homeRepository: HomeRepository,
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModel() {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()
    private val recyclerList = mutableListOf<RecyclerItem>()

    init {
        viewModelScope.launch {
            getCourses()
        }
    }

    suspend fun getCourses() {
        homeRepository.getCourses().collect {
            when (it) {
                is Resource.Success -> {
                    it.data.result.map { course ->
                        recyclerList.add(
                            RecyclerItem(StoreData(StoreDto(true, academyDto = course)))
                        )
                    }
                    viewModelScope.launch {
                        getProducts()
                    }
                }
                is Resource.Failure -> {

                }
                else -> {}
            }
        }
    }

    private suspend fun getProducts() {
        homeRepository.getProducts().collect {
            when (it) {
                is Resource.Success -> {
                    it.data.products.map { product ->
                        recyclerList.add(
                            RecyclerItem(StoreData(StoreDto(false, product = product)))
                        )
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
                }
                is Resource.Failure -> {

                }
                else -> {}
            }
        }
    }

    suspend fun addCartItem(item: CartItem){
        shoppingCartRepository.addCartItem(item)
    }
}