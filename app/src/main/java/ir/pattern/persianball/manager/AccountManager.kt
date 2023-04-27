package ir.pattern.persianball.manager

import ir.pattern.persianball.utils.SharedPreferenceUtils
import javax.inject.Inject

class AccountManager
@Inject
constructor(
    var sharedPreferenceUtils: SharedPreferenceUtils
) {

    val isLogin: Boolean
        get() {
            return getUsername().isNotBlank()
        }

    fun updatePassword(password: String) {
        sharedPreferenceUtils.updatePassword(password)
    }

    fun getRefreshToken(): String = sharedPreferenceUtils.getUserCredentials().refreshToken

    fun getUsername() : String = sharedPreferenceUtils.getUserCredentials().username

    fun updateAccessToken(token: String?){
        sharedPreferenceUtils.updateAccessToken(token)
    }

    fun updateUsername(username: String?){
        sharedPreferenceUtils.updateUsername(username)
    }
}