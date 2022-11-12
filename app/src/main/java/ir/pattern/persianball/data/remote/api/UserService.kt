package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.model.profile.PersonalInfoCreate
import ir.pattern.persianball.data.model.profile.PersonalInformationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

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
    suspend fun getUser(): Response<PersonalInformationDto>
}