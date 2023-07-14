package ir.pattern.persianball.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.repository.remote.datasource.LoginRemoteDataSource
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartData
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    private val loginRepository: LoginRepository,
    private val accountManager: AccountManager,
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val shoppingCartRepository: ShoppingCartRepository,
    val sharedPreferenceUtils: SharedPreferenceUtils,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _isLogin = MutableSharedFlow<Boolean>()
    val isLogin = _isLogin.asSharedFlow()
    private val _shopBadge = MutableSharedFlow<Int>()
    val shopBadge = _shopBadge.asSharedFlow()
    private val _notificationBadge = MutableSharedFlow<List<Int>>()
    val notificationBadge = _notificationBadge.asSharedFlow()
    val messageIdList = mutableListOf<Int>()
    private val _avatar = MutableSharedFlow<String?>()
    val avatar = _avatar.asSharedFlow()
    var userAvatar : String? = null
    suspend fun refreshToken() {
        if (accountManager.getRefreshToken().isNotBlank()) {
            when (val result =
                loginRemoteDataSource.refreshToken(RefreshTokenDto(accountManager.getRefreshToken()))) {
                is Resource.Success<TokenDto?> -> {
                    accountManager.updateAccessToken(result.data?.access)
                    viewModelScope.launch {
                        _isLogin.emit(true)
                        profileRepository._isLogin.emit(true)
                    }
                }

                is Resource.Failure<*> -> {
                        viewModelScope.launch {
                            _isLogin.emit(false)
                        }
                }
                else -> {}
            }
        }
    }

    suspend fun getUser(){
        viewModelScope.launch {
            when (val result = profileRepository.getUserPersonalData()) {
                is Resource.Success -> {
                    _avatar.emit(result.data.avatar)
                    //profileRepository.user = result.data
                    userAvatar = result.data.avatar
                }
                else -> Unit
            }
        }
    }

    fun getProfileImage(): String? {
        return profileRepository.user?.avatar
    }

    suspend fun getShoppingCart() {
        shoppingCartRepository.getShoppingCart().collect {
            when (it) {
                is Resource.Success -> {
                    if (!it.data.result.isEmpty()) {
                        _shopBadge.emit(it.data.result[0].items.size)
                        shoppingCartRepository.basketList = it.data.result[0].items
                    }else {
                        _shopBadge.emit(0)
                    }
                }
                is Resource.Failure -> {
                    _shopBadge.emit(0)
                }
                else -> {
                    _shopBadge.emit(0)
                }
            }
        }
    }

    suspend fun getMessage(){
        profileRepository.getUserMessage().collect{
            when (it) {
                is Resource.Success -> {
                    it.data?.results?.map { messageDto ->
                        messageIdList.add(messageDto.id)
                    }
                    _notificationBadge.emit(messageIdList)
                }

                is Resource.Failure -> {
                }

                else -> {}
            }
        }
    }

    suspend fun sendDevice(device: DeviceDto) {
        loginRemoteDataSource.userDevice(device)
    }

    suspend fun sendUserDevice(userDevice: UserDeviceDto){
        loginRemoteDataSource.sendUserDevice(userDevice)
    }

    fun getIsLogin() = profileRepository.isLogin
}