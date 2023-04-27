package ir.pattern.persianball.data.repository.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.repository.remote.api.DashboardService
import ir.pattern.persianball.data.repository.remote.api.HomeService
import ir.pattern.persianball.data.repository.remote.api.Request
import ir.pattern.persianball.error.ErrorTranslator
import javax.inject.Inject

class DashboardDataSource
@Inject constructor(
    private val dashboardService: DashboardService,
    private val errorTranslator: ErrorTranslator
) {
    suspend fun getDashboard() : Resource<DashboardsDto> {
        return Request.getResponse(request = {dashboardService.getDashboard()}, errorTranslator)
    }
}