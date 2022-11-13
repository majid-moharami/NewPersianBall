package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.profile.*
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @POST("order/address/")
    suspend fun createUserAddress(@Body address: Address): Response<Any?>

    @GET("order/address/")
    suspend fun getUserAddress(): Response<AddressDto>

    @PUT("order/address/1")
    suspend fun updateUserAddress(@Body address: Address): Response<Any?>

    @POST("user/")
    suspend fun createUser(@Body personalInfoCreate: PersonalInfoCreate): Response<Any?>

    @GET("user/")
    suspend fun getUser(): Response<PersonalDto>

    @PUT("user/{username}/")
    suspend fun updateUserPersonalData(
        @Path("username") username: String,
        @Body personalDto: PersonalDto
    ): Response<PersonalDto>
}