package ir.pattern.persianball.data.repository.remote.api

import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.home.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeService {

    @GET("product/course/")
    suspend fun getCourse() : Response<Academy>

    @GET("product/course/")
    suspend fun getAcademy() : Response<Academy>

    @GET("product/product/")
    suspend fun getProduct() : Response<Products>

    @GET("gallery/slider/")
    suspend fun getGallery() : Response<SliderList>

    @POST("user/register/")
    suspend fun register(@Body request: SignUp): Response<Any>

    @POST("user/code/verify/")
    suspend fun verifyUser(@Body verifyUser: VerifyUser) : Response<TokenResultDto>

    @POST("user/forget/")
    suspend fun forgetPassword(@Body forgetPassword: ForgetPassword): Response<Any>

    @POST("user/forget/setpassword/")
    suspend fun changePassword(@Body changePassword: ChangePassword): Response<Any>
}