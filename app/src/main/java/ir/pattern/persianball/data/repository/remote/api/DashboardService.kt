package ir.pattern.persianball.data.repository.remote.api

import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.dashboard.DashboardsDto
import ir.pattern.persianball.data.model.dashboard.OrderResultDto
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {
    @GET("course/user-course/")
    suspend fun getDashboard() : Response<DashboardsDto>

    @GET("order/order/")
    suspend fun getOrders() : Response<OrderResultDto>
}