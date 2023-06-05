package ir.pattern.persianball.data.repository

import androidx.paging.PagingData
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.model.dashboard.OrderResultDto
import ir.pattern.persianball.data.repository.remote.datasource.DashboardDataSource
import ir.pattern.persianball.data.repository.remote.datasource.HomeRemoteDataSource
import ir.pattern.persianball.presenter.feature.setting.registered.recycler.RegisteredCoursesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository
@Inject constructor(
    private val dashboardDataSource: DashboardDataSource
){

    var userCourse : DashboardsDto? = null
    suspend fun getDashboard(): Flow<Resource<DashboardsDto>> {
        return flow {
            val result = dashboardDataSource.getDashboard()
            when(result){
                is Resource.Success -> {
                    userCourse = result.data
                }
                else -> {}
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getOrder(): Flow<Resource<OrderResultDto>> {
        return flow {
            val result = dashboardDataSource.getOrder()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}