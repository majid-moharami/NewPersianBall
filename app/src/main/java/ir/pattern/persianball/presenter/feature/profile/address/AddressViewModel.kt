package ir.pattern.persianball.presenter.feature.profile.address

import android.icu.text.IDNA
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.adapter.mapToRecyclerItem
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileInformationData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel
@Inject constructor(
    val profileRepository: ProfileRepository
) : ViewModel() {

    val addressInfo: SharedFlow<Address?> = profileRepository.address

    init {
        viewModelScope.launch {
            getAddress()
        }
    }

    suspend fun getAddress() {
        profileRepository.getAddress().collect {
            when (it) {
                is Resource.Success -> {
                    profileRepository.setAddress(it.data.result[0])
                }
                else -> {}
            }
        }
    }

    suspend fun updateAddress(address: Address) {
        profileRepository.setAddress(address)
    }

    suspend fun createAddress(address: Address){
        profileRepository.createAddress(address).collect{

        }
    }
}