package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.RefreshTokenDto
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.repository.remote.datasource.LoginRemoteDataSource
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject
constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val sharedPreferenceUtils: SharedPreferenceUtils
){

    suspend fun login(login: Login): Flow<Resource<TokenDto?>>{
        return flow {
            val result = loginRemoteDataSource.login(login)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerUser(signUpRequest: SignUp): Flow<Resource<Any?>> {
        return flow {
            val result = loginRemoteDataSource.registerUser(signUpRequest)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun verifyUser(verifyUser: VerifyUser): Flow<Resource<TokenDto?>> {
        return flow{
            val result = loginRemoteDataSource.verifyUser(verifyUser)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun retryCode(retryCode: RetryCode): Flow<Resource<Any?>>{
        return flow {
            val result = loginRemoteDataSource.retryCode(retryCode)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword):  Flow<Resource<Any?>>{
        return flow {
            val result = loginRemoteDataSource.forgetPassword(forgetPassword)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun changePassword(changePassword: ChangePassword): Flow<Resource<Any?>>{
        return flow {
            val result = loginRemoteDataSource.changePassword(changePassword)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun refreshToken(refreshTokenDto: RefreshTokenDto): Flow<Resource<TokenDto?>>{
        return flow {
            val result= loginRemoteDataSource.refreshToken(refreshTokenDto)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun logout(){
        sharedPreferenceUtils.clearCredentials()
    }
}