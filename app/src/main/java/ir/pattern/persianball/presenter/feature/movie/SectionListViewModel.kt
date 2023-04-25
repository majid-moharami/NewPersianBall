package ir.pattern.persianball.presenter.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.academy.MovieDetailDto
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionHeaderData
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionItemData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SectionListViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val homeRepository: HomeRepository
) : BaseViewModel() {
    var allList = mutableListOf<RecyclerItem>()

    private val _academyDto = MutableSharedFlow<Resource<AcademyDto>>()
    val academyDto = _academyDto.asSharedFlow()
    lateinit var detail: MovieDetailDto

    fun updateSections(sectionHeader: SectionHeaderData) {
        val tempList = mutableListOf<RecyclerItem>()
        var selectedOpen: Boolean? = null
        var selectedHeaderId: String = ""
        allList.map {
            if (it.data is SectionHeaderData) {
                if ((it.data as SectionHeaderData).id == sectionHeader.id) {
                    selectedHeaderId = (it.data as SectionHeaderData).id
                    selectedOpen = (it.data as SectionHeaderData).isOpen
                    val selectedSectionHeader =
                        (it.data as SectionHeaderData).apply { isOpen = !isOpen }
                    tempList.add(RecyclerItem(selectedSectionHeader))
                    if (selectedOpen == false) {
                        (it.data as SectionHeaderData).sections?.sections?.map { sectionDto ->
                            tempList.add(RecyclerItem(SectionItemData(selectedHeaderId, sectionDto)))
                        }
                    } else {

                    }
                } else {
                    tempList.add(it)
                }
            } else {
                if ((it.data as SectionItemData).headerId == selectedHeaderId && selectedOpen == true) {

                } else {
                    tempList.add(it)
                }
            }
        }
        allList = tempList
        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(allList)))
    }

    suspend fun getAcademyById(id: Int) {
        _academyDto.emit(Resource.Loading())
        homeRepository.getAcademy().collect {
            when (it) {
                is Resource.Success -> {
                    it.data.result.map { academy ->
                        if (academy.id == id) {
                            detail = academy.detail
                            _academyDto.emit(Resource.Success(academy))
                        }
                    }
                }
                is Resource.Failure -> {
                    _academyDto.emit(Resource.Failure(it.error))
                }
                else -> {}
            }
        }
    }
}