package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.remote.api.LoginService
import ir.pattern.persianball.data.remote.api.Request
import javax.inject.Inject

class LoginRemoteDataSource
@Inject constructor(
    private val loginService: LoginService
) {
    suspend fun registerUser(signUpRequest: SignUp): Resource<Any?>{
        return Request.getResponse(
            request = {loginService.register(signUpRequest)},
            defaultErrorMessage = "Error posting register info"
        )
    }

    suspend fun verifyUser(verifyUser: VerifyUser): Resource<TokenDto?>{
        return Request.getResponse(
            request = {loginService.verifyUser(verifyUser)},
            defaultErrorMessage = "Error posting register info"
        )
    }

    suspend fun retryCode(retryCode: RetryCode): Resource<Any?>{
        return Request.getResponse(
            request = {loginService.retryCode(retryCode)},
            defaultErrorMessage = "Error posting retrying code"
        )
    }

    suspend fun login(login: Login): Resource<TokenDto?>{
        return Request.getResponse(
            request = {loginService.login(login)},
            defaultErrorMessage = "Error Login"
        )
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword): Resource<Any?>{
        return Request.getResponse(
            request = {loginService.forgetPassword(forgetPassword)},
            defaultErrorMessage = "Error forget password"
        )
    }

    suspend fun changePassword(changePassword: ChangePassword): Resource<Any?>{
        return Request.getResponse(
            request = {loginService.changePassword(changePassword)},
            defaultErrorMessage = "Error change password"
        )
    }

    suspend fun refreshToken(refreshTokenDto: RefreshTokenDto): Resource<TokenDto?>{
        return Request.getResponse(
            request = {loginService.refreshToken(refreshTokenDto)},
            defaultErrorMessage = "Error change password"
        )
    }
}