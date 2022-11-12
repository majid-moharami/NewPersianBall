package ir.pattern.persianball.manager

import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.repository.LoginRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator
@Inject
constructor(
    private val accountManager: AccountManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
//        runBlocking {
//            accountManager.refresh()
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
}