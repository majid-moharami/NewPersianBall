package ir.pattern.persianball.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.RefreshTokenDto
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.TokenDto
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.model.base.ErrorDTO
import ir.pattern.persianball.data.remote.datasource.LoginRemoteDataSource
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.manager.AccountManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val loginRepository: LoginRepository,
    private val accountManager: AccountManager,
    private val loginRemoteDataSource: LoginRemoteDataSource
) : ViewModel() {

    val _isLogin = MutableSharedFlow<Boolean>()
    val isLogin = _isLogin.asSharedFlow()
    suspend fun refreshToken() {
        viewModelScope.launch {
            _isLogin.emit(false)
        }
        val s =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoicmVmcmVzaCIsImV4cCI6MTY2ODk0MTU4MCwiaWF0IjoxNjY4MDc3NTgwLCJqdGkiOiIzMDdkZTVhOGNiNWM0ZjRmYjAwNTNlYmEwYWU3N2U1NSIsInVzZXJfaWQiOjM5fQ.57nFcbJKbXzdAgfvp2ZNLV5CymSDkRJR9zOzOLhOnBcs"
        if (accountManager.getRefreshToken().isNotBlank()) {
            when (val result =
                loginRemoteDataSource.refreshToken(RefreshTokenDto(accountManager.getRefreshToken()))) {
                is Resource.Success<TokenDto?> -> {
                    accountManager.updateAccessToken(result.data?.access)
                    viewModelScope.launch {
                        _isLogin.emit(true)
                    }
                }

                is Resource.Failure<*> -> {
                    if (result.error.code == ErrorDTO.CODE_SERVER_SING_OUT ||
                        result.error.code == ErrorDTO.CODE_ACCESS_DENIED
                    ) {
                        loginRepository.logout()
                        viewModelScope.launch {
                            _isLogin.emit(false)
                        }
                    }
                }
                else -> {}
            }
        }
    }
}