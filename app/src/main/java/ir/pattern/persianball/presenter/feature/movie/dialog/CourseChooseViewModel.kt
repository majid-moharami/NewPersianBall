package ir.pattern.persianball.presenter.feature.movie.dialog

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CourseChooseViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(){

}