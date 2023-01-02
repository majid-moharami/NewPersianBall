package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.remote.datasource.DashboardDataSource
import ir.pattern.persianball.data.remote.datasource.HomeRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository
@Inject constructor(
    private val dashboardDataSource: DashboardDataSource
){
    suspend fun getDashboard(): Flow<Resource<DashboardsDto>> {
        return flow {
            val result = dashboardDataSource.getDashboard()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}