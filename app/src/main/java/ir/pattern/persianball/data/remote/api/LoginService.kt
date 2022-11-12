package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("login/")
    suspend fun login(@Body login: Login): Response<TokenDto>

    @POST("register/")
    suspend fun register(@Body request: SignUp): Response<Any>

    @POST("code/verify/")
    suspend fun verifyUser(@Body verifyUser: VerifyUser) : Response<TokenDto>

    @POST("code/retry/")
    suspend fun retryCode(@Body retryCode: RetryCode) : Response<Any>

    @POST("forget/")
    suspend fun forgetPassword(@Body forgetPassword: ForgetPassword): Response<Any>

    @POST("forget/setpassword/")
    suspend fun changePassword(@Body changePassword: ChangePassword): Response<Any>

    @POST("login/refresh/")
    suspend fun refreshToken(@Body refreshTokenDto: RefreshTokenDto): Response<TokenDto>
}