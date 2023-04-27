package ir.pattern.persianball.data.repository.remote.api

import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.home.*
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("product/course/")
    suspend fun getCourse() : Response<Academy>

    @GET("product/course/")
    suspend fun getAcademy() : Response<Academy>

    @GET("product/product/")
    suspend fun getProduct() : Response<Products>

    @GET("gallery/slider/")
    suspend fun getGallery() : Response<SliderList>

}