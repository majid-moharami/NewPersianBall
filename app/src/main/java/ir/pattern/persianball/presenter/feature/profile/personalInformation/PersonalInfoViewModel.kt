package ir.pattern.persianball.presenter.feature.profile.personalInformation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileImageData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileInformationData
import ir.pattern.persianball.presenter.feature.profile.recycler.ProfileNameData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PersonalInfoViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel()   {
//    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
//    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()
//    val list = mutableListOf<RecyclerItem>(
//        RecyclerItem(InfoItemData(ItemInfoDto("نام", "ایمان"))),
//        RecyclerItem(InfoItemData(ItemInfoDto(" نام لاتین", "Iman"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("نام خانوادگی", "شهریاری"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("نام خانوادگی لاتین", "shahriari"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("تاریخ تولد", "1388/10/02"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("ملیت", "ایرانی"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("کد ملی", "123456789"))),
//        RecyclerItem(InfoItemData(ItemInfoDto("جنسیت", "مرد")))
//    )
//    init {
//        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
//    }
//
//    fun updateList(itemInfoDto: ItemInfoDto){
//        for(i in list){
//            val d = i.data as InfoItemData
//            if (d.itemInfoDto.title == itemInfoDto.title){
//                (i.data as InfoItemData).itemInfoDto.content = itemInfoDto.content
//            }
//        }
//        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
//    }
}