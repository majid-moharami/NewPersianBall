package ir.pattern.persianball.presenter.feature.message

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.UserMessagesDto
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.message.recycler.MessageData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PushMessageViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    val ids = mutableListOf<Int>()

    suspend fun getMessages(listId: List<Int>) {
        profileRepository.getUserMessage().collect {
            when (it) {
                is Resource.Success -> {
                    val list = mutableListOf<RecyclerItem>()
                    it.data?.results?.map { messageDto ->
                        ids.add(messageDto.id)
                        var isNew = true
                        for (i in listId){
                            if (i == messageDto.id) isNew = false
                        }
                        list.add(RecyclerItem(MessageData(messageDto, isNew)))
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(list)))
                }

                is Resource.Failure -> {
                }

                else -> {}
            }
        }
    }
}