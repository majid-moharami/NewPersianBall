package ir.pattern.persianball.presenter.feature.profile.personalInformation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalInfoViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    var personalData = PersonalDto()

//    init {
//        getPersonalData()
//    }

    private val _userPersonalData = MutableStateFlow<PersonalDto?>(null)
    val userPersonalData: StateFlow<PersonalDto?> = _userPersonalData.asStateFlow()

    private val _userUpdatePersonalData = MutableSharedFlow<Resource<PersonalDto?>>()
    val userUpdatePersonalData: SharedFlow<Resource<PersonalDto?>> =
        _userUpdatePersonalData.asSharedFlow()

    fun getPersonalData() {
        viewModelScope.launch {
            when (val result = profileRepository.getUserPersonalData()) {
                is Resource.Success -> {
                    _userPersonalData.value = result.data
                }
                else -> Unit
            }
        }
    }

    fun updatePersonalData(personalDto: PersonalDto) {
        personalData.avatar = null
        viewModelScope.launch {
            _userUpdatePersonalData.emit(Resource.Loading())
            _userUpdatePersonalData.emit(profileRepository.updatePersonalData(personalDto))
        }
    }
}