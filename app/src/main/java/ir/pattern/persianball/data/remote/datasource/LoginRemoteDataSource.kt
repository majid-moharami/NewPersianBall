package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.remote.api.LoginService
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.error.ErrorTranslator
import javax.inject.Inject

class LoginRemoteDataSource
@Inject constructor(
    private val loginService: LoginService,
    private val errorTranslator: ErrorTranslator
) {
    suspend fun registerUser(signUpRequest: SignUp): Resource<Any?>{
        return Request.getResponse(request = {loginService.register(signUpRequest)}, errorTranslator)
    }

    suspend fun verifyUser(verifyUser: VerifyUser): Resource<TokenDto?>{
        return Request.getResponse(request = {loginService.verifyUser(verifyUser)}, errorTranslator)
    }

    suspend fun retryCode(retryCode: RetryCode): Resource<Any?>{
        return Request.getResponse(request = {loginService.retryCode(retryCode)}, errorTranslator)
    }

    suspend fun login(login: Login): Resource<TokenDto?>{
        return Request.getResponse(request = {loginService.login(login)}, errorTranslator)
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword): Resource<Any?>{
        return Request.getResponse(request = {loginService.forgetPassword(forgetPassword)}, errorTranslator)
    }

    suspend fun changePassword(changePassword: ChangePassword): Resource<Any?>{
        return Request.getResponse(request = {loginService.changePassword(changePassword)}, errorTranslator)
    }

    suspend fun refreshToken(refreshTokenDto: RefreshTokenDto): Resource<TokenDto?>{
        return Request.getResponse(request = {loginService.refreshToken(refreshTokenDto)}, errorTranslator)
    }
}