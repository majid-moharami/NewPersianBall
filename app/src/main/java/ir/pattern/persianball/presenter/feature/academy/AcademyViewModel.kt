package ir.pattern.persianball.presenter.feature.academy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeProductData
import ir.pattern.persianball.presenter.feature.home.recycler.HomeRecyclerHeaderData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AcademyViewModel
@Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()
    private val recyclerList = mutableListOf<RecyclerItem>()
    private val _cartList = MutableSharedFlow<Resource<Any?>>()
    val cartList = _cartList.asSharedFlow()

    init {
        if (_recyclerItems.value == null) {
            viewModelScope.launch {
                getAcademy()
            }
        }
    }

    suspend fun getAcademy() {
        _cartList.emit(Resource.Loading())
        if (recyclerList.isEmpty()) {
            homeRepository.getAcademy().collect {
                when (it) {
                    is Resource.Success -> {
                        _cartList.emit(it)
                        it.data.result.map { academyDto ->
                            recyclerList.add(RecyclerItem(AcademyCourseData(academyDto)))
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
    }
}