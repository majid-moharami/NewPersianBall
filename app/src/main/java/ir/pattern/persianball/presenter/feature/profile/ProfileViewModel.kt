package ir.pattern.persianball.presenter.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileImageData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileInformationData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileNameData
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val profileRepository: ProfileRepository,
    var sharedPreferenceUtils: SharedPreferenceUtils
) : ViewModel() {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()

    private val _avatar = MutableStateFlow<String?>(null)
    val avatar: StateFlow<String?> = _avatar.asStateFlow()

    private val _name = MutableStateFlow<String?>(null)
    val name: StateFlow<String?> = _name.asStateFlow()

    suspend fun setName(s: String) {
        _name.emit(s)
    }

    fun setUpData() {
        _name.value = sharedPreferenceUtils.getUserCredentials().username
        val list = mutableListOf<RecyclerItem>()
        list.add(RecyclerItem(ProfileImageData(avatar)))
        list.add(RecyclerItem(ProfileNameData(name = name)))
        list.add(RecyclerItem(ProfileInformationData()))
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
    }

    fun updateUserInfo(personalDto: PersonalDto) {
//        viewModelScope.launch {
//            profileRepository.updatePersonalData(personalDto)
//            profileRepository.getUserPersonalData()
//        }
//        if (!personalDto.firstName.isNullOrEmpty() || !personalDto.lastName.isNullOrEmpty()) {
        _recyclerItems.value?.also {
            _recyclerItems.value = RecyclerData(it.pagingFlow.map {
                it.map { rvItem ->
                    if (rvItem.data is ProfileNameData) {
                        if (personalDto.firstName != "null") {
                            (rvItem.data as ProfileNameData).firstName = personalDto.firstName
                        }
                        if (personalDto.lastName != "null") {
                            (rvItem.data as ProfileNameData).lastName = personalDto.lastName
                        }
                        rvItem
                    } else if (rvItem.data is ProfileImageData) {
                        _avatar.emit(personalDto.avatar)
                        sharedPreferenceUtils.updateProfileImage(personalDto.avatar)
                        rvItem
                    } else {
                        rvItem
                    }
                }
            })
        }
//        }
    }

    fun uploadAvatar(username: String, file: MultipartBody.Part) {
        viewModelScope.launch {
            val result = profileRepository.uploadAvatar(username, file)
            if (result is Resource.Success) {
                _avatar.emit(result.data.avatar)
                sharedPreferenceUtils.updateProfileImage(result.data.avatar)
            }
        }
    }
}