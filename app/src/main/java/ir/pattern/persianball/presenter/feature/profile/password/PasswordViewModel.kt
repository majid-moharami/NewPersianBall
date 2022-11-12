package ir.pattern.persianball.presenter.feature.profile.password

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.presenter.feature.profile.password.recycler.PasswordItemsData
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
@HiltViewModel
class PasswordViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()

    init {
        val list = mutableListOf<RecyclerItem>()
        list.add(RecyclerItem(PasswordItemsData(ItemInfoDto("رمز عبور پیشین", "0921567469"))))
        list.add(RecyclerItem(PasswordItemsData(ItemInfoDto("رمز عبور جدید", "02158676945"))))
        list.add(RecyclerItem(PasswordItemsData(ItemInfoDto("تکرار رمز عبور جدید", "mahtaabdarbaad@gmail.com"))))
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
    }
}