package ir.pattern.persianball.data.repository

import android.app.Person
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.paging.PagingSourceSinglePage
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.data.model.profile.PersonalInformationDto
import ir.pattern.persianball.data.remote.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
}