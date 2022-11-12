package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.api.UserService
import javax.inject.Inject

class UserRemoteDataSource
@Inject constructor(
    private val userService: UserService
) {
    suspend fun createUserAddress(address: Address): Resource<Any?> {
        return Request.getResponse(
            request = {userService.createUserAddress(address)},
            defaultErrorMessage = "Error posting register info"
        )
    }

    suspend fun createAddress(address: Address): Resource<Any?>{
        return Request.getResponse(
            request = {userService.createUserAddress(address)},
            defaultErrorMessage = "Error Login"
        )
    }

    suspend fun getUserAddress(): Resource<AddressDto>{
        return Request.getResponse(
            request = {userService.getUserAddress()},
            defaultErrorMessage = "Error posting register info"
        )
    }
}