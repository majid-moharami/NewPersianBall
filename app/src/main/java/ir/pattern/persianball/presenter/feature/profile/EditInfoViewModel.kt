package ir.pattern.persianball.presenter.feature.profile

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditInfoViewModel @Inject constructor() : ViewModel() {
    private val _dialogResult = MutableSharedFlow<Bundle>()
    val dialogResult = _dialogResult.asSharedFlow()

    fun setDialogResult(bundle: Bundle) {
        viewModelScope.launch {
            _dialogResult.emit(bundle)
        }
    }
}