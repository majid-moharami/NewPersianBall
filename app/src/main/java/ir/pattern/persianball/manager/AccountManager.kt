package ir.pattern.persianball.manager

import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.ChangePassword
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.remote.api.LoginService
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.datasource.LoginRemoteDataSource
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountManager
@Inject
constructor(
    var sharedPreferenceUtils: SharedPreferenceUtils,
    //private val loginService: LoginRemoteDataSource
) {

    val isLogin : Boolean
    get() {
        return sharedPreferenceUtils.getUserCredentials().username.isNotBlank()
    }

    fun updatePassword(password: String){
        sharedPreferenceUtils.updatePassword(password)
    }
//
//    suspend fun refresh(): String? {
//        var token: String? = null
//        flow {
//            val result= loginService.refreshToken(RefreshTokenDto(sharedPreferenceUtils.getUserCredentials().token))
//            emit(result)
//        }.flowOn(Dispatchers.IO).collect{
//            val username = sharedPreferenceUtils.getUserCredentials().username
//            val password = sharedPreferenceUtils.getUserCredentials().password
//            val accessToken = sharedPreferenceUtils.getUserCredentials().token
//            val refreshToken =
//                sharedPreferenceUtils.getUserCredentials().refreshToken
//            sharedPreferenceUtils.putUserCredentials(
//                User(username, password, accessToken, refreshToken)
//            )
//            token = (it as Resource.Success).data?.access
//        }
////        loginRepository.refreshToken(RefreshTokenDto(sharedPreferenceUtils.getUserCredentials().token))
////            .collect {
////                val username = sharedPreferenceUtils.getUserCredentials().username
////                val password = sharedPreferenceUtils.getUserCredentials().password
////                val accessToken = sharedPreferenceUtils.getUserCredentials().token
////                val refreshToken =
////                    sharedPreferenceUtils.getUserCredentials().refreshToken
////                sharedPreferenceUtils.putUserCredentials(
////                    User(username, password, accessToken, refreshToken)
////                )
////                token = (it as Resource.Success).data?.access
////            }
//        return token
//    }
}