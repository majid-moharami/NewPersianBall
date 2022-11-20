package ir.pattern.persianball.presenter.feature.profile.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.repository.ProfileRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel
@Inject constructor(
    val profileRepository: ProfileRepository
) : ViewModel() {

    private val _address = MutableStateFlow<Address?>(null)
    val addressInfo: SharedFlow<Address?> = _address.asStateFlow()
    var address: Address = Address()
    private val _addressResponse = MutableSharedFlow<Resource<Any?>>()
    val addressResponse = _addressResponse.asSharedFlow()

    init {
        viewModelScope.launch {
            getAddress()
        }
    }

    private suspend fun getAddress() {
        when (val result = profileRepository.getAddress()) {
            is Resource.Success -> {
                if (result.data.result.isNotEmpty()) {
                    _address.value = result.data.result.first()
                }
            }
            else -> Unit
        }
    }

    fun createAddress(address: Address) {
        viewModelScope.launch {
            _addressResponse.emit(Resource.Loading())
            address.id?.also {
                _addressResponse.emit(profileRepository.updateAddress(address))
            } ?: kotlin.run {
                _addressResponse.emit(profileRepository.createAddress(address))
            }
        }
    }
}