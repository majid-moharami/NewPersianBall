package ir.pattern.persianball.data.repository.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.model.dashboard.OrderResultDto
import ir.pattern.persianball.data.repository.remote.api.DashboardService
import ir.pattern.persianball.data.repository.remote.api.Request
import ir.pattern.persianball.data.model.ErrorTranslator
import javax.inject.Inject

class DashboardDataSource
@Inject constructor(
    private val dashboardService: DashboardService,
    private val errorTranslator: ErrorTranslator
) {
    suspend fun getDashboard() : Resource<DashboardsDto> {
        return Request.getResponse(request = {dashboardService.getDashboard()}, errorTranslator)
    }

    suspend fun getOrder() : Resource<OrderResultDto> {
        return Request.getResponse(request = {dashboardService.getOrders(50)}, errorTranslator)
    }
}