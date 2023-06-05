package ir.pattern.persianball.presenter.feature.setting.registered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.repository.DashboardRepository
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.presenter.feature.academy.recycler.AcademyCourseData
import ir.pattern.persianball.presenter.feature.setting.registered.recycler.RegisteredCoursesData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisteredCoursesViewModel
@Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()
    private val recyclerList = mutableListOf<RecyclerItem>()

    private val _cartList = MutableSharedFlow<Resource<Any?>>()
    val cartList = _cartList.asSharedFlow()

    private val _isEmpty = MutableSharedFlow<Boolean>()
    val isEmpty = _isEmpty.asSharedFlow()

    init {
        viewModelScope.launch {
            getDashboard()
        }
    }

    suspend fun getDashboard() {
        if (dashboardRepository.userCourse != null) {
            _isEmpty.emit(dashboardRepository.userCourse!!.results.isEmpty())
            dashboardRepository.userCourse!!.results.map { dashboardDto ->
                recyclerList.add(RecyclerItem(RegisteredCoursesData(dashboardDto)))
            }
            _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
        } else {
            dashboardRepository.getDashboard().collect {
                when (it) {
                    is Resource.Success -> {
                        _cartList.emit(it)
                        _isEmpty.emit(it.data.results.isEmpty())
                        it.data.results.map { dashboardDto ->
                            recyclerList.add(RecyclerItem(RegisteredCoursesData(dashboardDto)))
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