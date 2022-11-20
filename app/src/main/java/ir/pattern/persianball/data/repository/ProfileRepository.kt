package ir.pattern.persianball.data.repository

import android.app.Person
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.paging.PagingSourceSinglePage
import ir.pattern.persianball.data.model.profile.*
import ir.pattern.persianball.data.remote.datasource.UserRemoteDataSource
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
}