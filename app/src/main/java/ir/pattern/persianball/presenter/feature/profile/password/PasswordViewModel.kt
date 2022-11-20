package ir.pattern.persianball.presenter.feature.profile.password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.profile.ChangePasswordDto
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.presenter.feature.profile.password.recycler.PasswordItemsData
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import kotlinx.coroutines.flow.*
import javax.inject.Inject
@HiltViewModel
class PasswordViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _changePassState = MutableSharedFlow<Resource<Any?>>()
    val changePassState: SharedFlow<Resource<Any?>> = _changePassState.asSharedFlow()

    suspend fun changePassword(changePasswordDto: ChangePasswordDto){
        _changePassState.emit(Resource.Loading())
        _changePassState.emit(profileRepository.changePassword(changePasswordDto))
    }
}