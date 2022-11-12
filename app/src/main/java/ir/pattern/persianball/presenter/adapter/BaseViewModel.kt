package ir.pattern.persianball.presenter.adapter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.base.RecyclerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    private val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()

    fun setRecyclerItems(executor: suspend () -> RecyclerData?) {
        viewModelScope.launch {
            executor.invoke()?.also {
                _recyclerItems.value = it
            }
        }
    }

    fun setRecyclerItems(data: RecyclerData) {
        _recyclerItems.value = data
    }
}