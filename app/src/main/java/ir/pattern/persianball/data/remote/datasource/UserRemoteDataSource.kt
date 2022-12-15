package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.profile.*
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.api.UserService
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteDataSource
@Inject constructor(
    private val userService: UserService
) {
    suspend fun createUserAddress(address: Address): Resource<Any?> {
        return Request.getResponse(
            request = {userService.createUserAddress(address)}
        )
    }

    suspend fun createAddress(address: Address): Resource<Any?>{
        return Request.getResponse(
            request = {userService.createUserAddress(address)}
        )
    }

    suspend fun uploadAvatar(username: String, file: MultipartBody.Part): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.uploadAvatar(username, file)}
        )
    }

    suspend fun getUserAddress(): Resource<AddressDto>{
        return Request.getResponse(
            request = {userService.getUserAddress()}
        )
    }

    suspend fun getPersonalData(): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.getUser()}
        )
    }

    suspend fun updatePersonalData(personalDto: PersonalDto): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.updateUserPersonalData(personalDto.username, personalDto)}
        )
    }

    suspend fun changePassword(changePasswordDto: ChangePasswordDto): Resource<*>{
        return Request.getResponse(
            request = {userService.changePassword(changePasswordDto)}
        )
    }

    suspend fun updateAddress(address: Address): Resource<Address?>{
        val id = address.id ?: 0
        address.id = null
        return Request.getResponse(
            request = {userService.updateUserAddress(id, address)}
        )
    }
}