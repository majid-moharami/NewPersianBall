package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.remote.api.DashboardService
import ir.pattern.persianball.data.remote.api.HomeService
import ir.pattern.persianball.data.remote.api.Request
import javax.inject.Inject

class DashboardDataSource
@Inject constructor(
    private val dashboardService: DashboardService
) {
    suspend fun getDashboard() : Resource<DashboardsDto> {
        return Request.getResponse(request = {dashboardService.getDashboard()})
    }
}