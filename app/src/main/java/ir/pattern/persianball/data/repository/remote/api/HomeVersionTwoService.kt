package ir.pattern.persianball.data.repository.remote.api

import ir.pattern.persianball.data.model.VerifyUser
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.home.Products
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeVersionTwoService {
    @GET("product/course/")
    suspend fun getCourse(@Query("limit") limit: Int) : Response<Academy>

    @GET("product/course/")
    suspend fun getAcademy(@Query("limit") limit: Int) : Response<Academy>

    @GET("product/course/{id}/")
    suspend fun getCourseDetail(@Path("id") id: Int) : Response<AcademyDto>

    @GET("product/product/")
    suspend fun getProduct(@Query("limit") limit: Int) : Response<Products>

    @GET("product/product/{id}/")
    suspend fun getProductDetail(@Path("id") id: Int) : Response<Product>
}