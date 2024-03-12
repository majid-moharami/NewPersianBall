package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.UserMessagesDto
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.model.profile.ChangePasswordDto
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.data.repository.remote.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository
@Inject
constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) {

    var user : PersonalDto? = null
    var username: String? = null

    var userAddress : AddressDto? = null

    val _isLogin = MutableStateFlow<Boolean?>(null)
    val isLogin: StateFlow<Boolean?> = _isLogin.asStateFlow()
    suspend fun getAddress(): Resource<AddressDto> = userRemoteDataSource.getUserAddress()

    suspend fun getUserPersonalData(): Resource<PersonalDto> =
        userRemoteDataSource.getPersonalData()

    suspend fun updatePersonalData(personalDto: PersonalDto): Resource<PersonalDto> =
        userRemoteDataSource.updatePersonalData(personalDto)

    suspend fun createAddress(address: Address): Resource<Any?> =
        userRemoteDataSource.createAddress(address)

    suspend fun uploadAvatar(username: String, file: MultipartBody.Part): Resource<PersonalDto> =
        userRemoteDataSource.uploadAvatar(username, file)

    suspend fun changePassword(changePasswordDto: ChangePasswordDto): Resource<*> =
        userRemoteDataSource.changePassword(changePasswordDto)

    suspend fun updateAddress(address: Address): Resource<Address?> =
        userRemoteDataSource.updateAddress(address)

    suspend fun getUserMessage(): Flow<Resource<UserMessagesDto?>> {
        return flow {
            val result = userRemoteDataSource.getUserMessages()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}