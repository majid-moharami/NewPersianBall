package ir.pattern.persianball.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.paging.PagingSourceSinglePage
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
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
    protected val _address = MutableSharedFlow<Address?>()
    val address: SharedFlow<Address?> = _address.asSharedFlow()

    protected val _user = MutableSharedFlow<PersonalInformationDto?>()
    val user: SharedFlow<Address?> = _address.asSharedFlow()

    suspend fun setAddress(address: Address?){
        _address.emit(address)
    }

    suspend fun getAddress(): Flow<Resource<AddressDto>> {
        return flow {
            val result = userRemoteDataSource.getUserAddress()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun createAddress(address: Address): Flow<Resource<Any?>>{
        return flow {
            val result = userRemoteDataSource.createAddress(address)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}