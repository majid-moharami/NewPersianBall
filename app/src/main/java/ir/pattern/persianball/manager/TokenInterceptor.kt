package ir.pattern.persianball.manager

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor
@Inject
constructor(
    var accountManager: AccountManager
) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val token = accountManager.sharedPreferenceUtils.getUserCredentials().token

        val request = original.newBuilder()
            .addHeader("Authorization", "Token $token")
            .url(originalHttpUrl)
            .build()
        return chain.proceed(request)
    }
}