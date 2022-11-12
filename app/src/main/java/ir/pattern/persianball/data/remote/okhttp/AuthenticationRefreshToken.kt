package ir.pattern.persianball.data.remote.okhttp

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthenticationRefreshToken @Inject constructor() : Interceptor {


    //    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//        val authenticationRequest = request(originalRequest)
//        val initialResponse = chain.proceed(authenticationRequest)
//        when (initialResponse.code) {
//            403, 401 -> {
//                val responseNewTokenLoginModel =
//                    refreshTokenApi.refreshToken(userManager.getRefreshToken()).execute()
//                return when {
//                    responseNewTokenLoginModel.code() != 200 -> {
//                        authManager.authExpiredAndGoLogin()
//                        initialResponse
//                    }
//                    else -> {
//                        responseNewTokenLoginModel.body()?.access?.let {
//                            userManager.updateAccessToken(it)
//                        }
//                        responseNewTokenLoginModel.body()?.refresh?.let {
//                            userManager.updateRefreshToken(it)
//                        }
//                        val newAuthenticationRequest = originalRequest.newBuilder().addHeader(
//                            "Authorization",
//                            "Token " + userManager.getAccessToken()
//                        ).build()
//                        initialResponse.close()
//                        chain.proceed(newAuthenticationRequest)
//                    }
//                }
//            }
//            else -> return initialResponse
//        }
//    }
//
//    private fun request(originalRequest: Request): Request {
//        return originalRequest.newBuilder()
//            .addHeader("Authorization", "Token ${userManager.getAccessToken()}").build()
//    }
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}