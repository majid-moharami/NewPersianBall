package ir.pattern.persianball.presenter.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileImageData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileInformationData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileNameData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel()  {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()

    init {
        val list = mutableListOf<RecyclerItem>()
        list.add(RecyclerItem(ProfileImageData()))
        list.add(RecyclerItem(ProfileNameData()))
        list.add(RecyclerItem(ProfileInformationData()))
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
    }
}