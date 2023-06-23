package ir.pattern.persianball.data.repository

import android.app.Person
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.UserMessagesDto
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.paging.PagingSourceSinglePage
import ir.pattern.persianball.data.model.profile.*
import ir.pattern.persianball.data.repository.remote.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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