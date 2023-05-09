package ir.pattern.persianball.data.repository.remote.datasource

import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.UserMessagesDto
import ir.pattern.persianball.data.model.profile.*
import ir.pattern.persianball.data.repository.remote.api.Request
import ir.pattern.persianball.data.repository.remote.api.UserService
import ir.pattern.persianball.error.ErrorTranslator
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteDataSource
@Inject constructor(
    private val userService: UserService,
    private val errorTranslator: ErrorTranslator
) {
    suspend fun createUserAddress(address: Address): Resource<Any?> {
        return Request.getResponse(
            request = {userService.createUserAddress(address)}, errorTranslator
        )
    }

    suspend fun createAddress(address: Address): Resource<Any?>{
        return Request.getResponse(
            request = {userService.createUserAddress(address)}, errorTranslator
        )
    }

    suspend fun uploadAvatar(username: String, file: MultipartBody.Part): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.uploadAvatar(username, file)}, errorTranslator
        )
    }

    suspend fun getUserAddress(): Resource<AddressDto>{
        return Request.getResponse(
            request = {userService.getUserAddress()}, errorTranslator
        )
    }

    suspend fun getPersonalData(): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.getUser()}, errorTranslator
        )
    }

    suspend fun updatePersonalData(personalDto: PersonalDto): Resource<PersonalDto>{
        return Request.getResponse(
            request = {userService.updateUserPersonalData(personalDto.username, personalDto)}, errorTranslator
        )
    }

    suspend fun changePassword(changePasswordDto: ChangePasswordDto): Resource<*>{
        return Request.getResponse(
            request = {userService.changePassword(changePasswordDto)}, errorTranslator
        )
    }

    suspend fun updateAddress(address: Address): Resource<Address?>{
        val id = address.id ?: 0
        address.id = null
        return Request.getResponse(
            request = {userService.updateUserAddress(id, address)}, errorTranslator
        )
    }

    suspend fun getUserMessages(): Resource<UserMessagesDto?>{
        return Request.getResponse(request = {userService.getMessages()}, errorTranslator)
    }
}