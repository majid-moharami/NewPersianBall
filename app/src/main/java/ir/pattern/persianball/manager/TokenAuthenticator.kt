package ir.pattern.persianball.manager

import ir.pattern.persianball.data.model.RefreshTokenDto
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.utils.SharedPreferenceUtils
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator
@Inject
constructor(
    private val accountManager: AccountManager,
    var sharedPreferenceUtils: SharedPreferenceUtils,
    private val loginRepository: Provider<LoginRepository>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
//        runBlocking {
//            refreshToken()
//        }
        return response
            .request
            .newBuilder()
            .header(
                "Authorization",
                "Token ${accountManager.sharedPreferenceUtils.getUserCredentials().token}"
            )
            .build()
    }

    private suspend fun refreshToken() : String?{
        var token: String? = null
        loginRepository.get().refreshToken(RefreshTokenDto(accountManager.getRefreshToken())).collect{
            val username = sharedPreferenceUtils.getUserCredentials().username
            val password = sharedPreferenceUtils.getUserCredentials().password
            val accessToken = sharedPreferenceUtils.getUserCredentials().token
            val refreshToken =
                sharedPreferenceUtils.getUserCredentials().refreshToken
            val profileImageUrl = sharedPreferenceUtils.getUserCredentials().profileImageUrl
            sharedPreferenceUtils.putUserCredentials(
                User(username, password, accessToken, refreshToken, profileImageUrl)
            )
            token = (it as Resource.Success).data?.access
        }
        return token
    }
//
//    fun isTokenExpire(JWTEncoded: String): Boolean? {
//        return try {
//            val split = JWTEncoded.split("\\.").toTypedArray()
//            val jsonObject = JSONObject(getJson(split[1]))
//            System.currentTimeMillis() > jsonObject.getLong("exp")
//        } catch (e: Exception) {
//            false
//        }
//    }
//
//    private fun getJson(strEncoded: String): String? {
//        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
//        return String(decodedBytes, StandardCharsets.UTF_8)
//    }
}