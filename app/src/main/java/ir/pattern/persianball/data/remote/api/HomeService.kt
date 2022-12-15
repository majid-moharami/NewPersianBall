package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.data.model.home.Gallery
import ir.pattern.persianball.data.model.home.Products
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    @GET("product/course/")
    suspend fun getCourse() : Response<Courses>

    @GET("product/product/")
    suspend fun getProduct() : Response<Products>

    @GET("gallery/image/")
    suspend fun getGallery() : Response<Gallery>

}