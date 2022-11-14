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
import javax.inject.Provider

class AccountManager
@Inject
constructor(
    var sharedPreferenceUtils: SharedPreferenceUtils
) {

    val isLogin: Boolean
        get() {
            return sharedPreferenceUtils.getUserCredentials().username.isNotBlank()
        }

    fun updatePassword(password: String) {
        sharedPreferenceUtils.updatePassword(password)
    }

    fun getRefreshToken(): String = sharedPreferenceUtils.getUserCredentials().token
}