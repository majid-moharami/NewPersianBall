package ir.pattern.persianball.presenter.feature.shopping.address

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SendingViewModel @Inject constructor() : ViewModel()  {
    private val _dialogResult = MutableSharedFlow<String>()
    val dialogResult = _dialogResult.asSharedFlow()


}