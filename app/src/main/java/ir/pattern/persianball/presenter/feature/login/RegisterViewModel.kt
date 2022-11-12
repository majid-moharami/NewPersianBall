package ir.pattern.persianball.presenter.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository
) : ViewModel(){

    val loginState = MutableSharedFlow<Resource<TokenDto?>>()
    val signUpState = MutableSharedFlow<Resource<Any?>>()
    val verifyState = MutableSharedFlow<Resource<TokenDto?>>()
    val forgetPasswordState = MutableSharedFlow<Resource<Any?>>()
    val changePasswordState = MutableSharedFlow<Resource<Any?>>()
    val retryCodeState = MutableSharedFlow<Resource<Any?>>()

    suspend fun login(login: Login){
        loginRepository.login(login).collect{
            loginState.emit(it)
        }
    }
    suspend fun signUpUser(signUp: SignUp){
        loginRepository.registerUser(signUp).collect{
            signUpState.emit(it)
        }
    }

    suspend fun verifyUser(verifyUser: VerifyUser){
        loginRepository.verifyUser(verifyUser).collect{
            verifyState.emit(it)
        }
    }

    suspend fun retryCode(retryCode: RetryCode){
        loginRepository.retryCode(retryCode).collect{
            retryCodeState.emit(it)
        }
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword){
        loginRepository.forgetPassword(forgetPassword).collect{
            forgetPasswordState.emit(it)
        }
    }

    suspend fun changePassword(changePassword: ChangePassword){
        loginRepository.changePassword(changePassword).collect{
            changePasswordState.emit(it)
        }
    }
}