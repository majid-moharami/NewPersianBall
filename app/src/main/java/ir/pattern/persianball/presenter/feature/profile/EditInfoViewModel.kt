package ir.pattern.persianball.presenter.feature.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel
@Inject
constructor(
    private val profileRepository: ProfileRepository
) : ViewModel(){
    val editIsSuccess = MutableSharedFlow<Any>()

}