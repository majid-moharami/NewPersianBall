package ir.pattern.persianball.presenter.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.*
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.home.recycler.HomeSliderData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingCartRepository: ShoppingCartRepository,
    val homeRepository: HomeRepository
) : BaseViewModel() {

    private val _academyDto = MutableSharedFlow<Resource<AcademyDto>>()
    val academyDto = _academyDto.asSharedFlow()
    lateinit var detail: MovieDetailDto

    private val _addingCartFlow = MutableSharedFlow<String>()
    val addingCartFlow = _addingCartFlow.asSharedFlow()

    var supportMap = mutableMapOf<CoachDto, List<GiftProductDto?>>()
    var locationMap = mutableMapOf<TimeAndLocationsDto, List<GiftProductDto?>>()

    private val _selectedCoach = MutableStateFlow<CoachDto?>(null)
    val selectedCoach = _selectedCoach.asStateFlow()
    private val _selectedLocation = MutableStateFlow<TimeAndLocationsDto?>(null)
    val selectedLocation = _selectedLocation.asStateFlow()
    private val _selectedGift = MutableStateFlow<GiftProductDto?>(null)
    val selectedGift = _selectedGift.asStateFlow()
    var selectedGifts: GiftProductDto? = null

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

    fun setAppropriateMap(movie: AcademyDto) {
        if (movie.category?.nameFarsi == "دوره ها") {
            val supportSet = mutableSetOf<CoachDto>()
            movie.variants.map {
                it?.coach?.also { coach ->
                    if (it.isActive) supportSet.add(coach)
                }
            }
            supportSet.map { coach ->
                val productList = mutableListOf<GiftProductDto>()
                movie.variants.map { variant ->
                    if (coach.fullName == variant?.coach?.fullName) {
                        if (variant.giftProduct != null && variant.isActive) {
                            productList.add(variant.giftProduct)
                        } else {
                            if (variant.isActive) {
                                productList.add(
                                    GiftProductDto(
                                        "",
                                        0,
                                        0,
                                        "",
                                        "",
                                        "",
                                        0,
                                        "بدون هدیه",
                                        "non-id"
                                    )
                                )
                            }
                        }
                    }
                }
                supportMap.put(coach, productList)
            }
            var noCoachDto: CoachDto? = null
            movie.variants.map {
                if (it?.coach == null) {
                    noCoachDto = CoachDto(-1, "بدون پشتیبان", "")
                    return@map
                }
            }
            if (noCoachDto != null) {
                val noCoachProductList = mutableListOf<GiftProductDto>()
                movie.variants.map {
                    if (it?.coach == null && it?.giftProduct != null && it.isActive) {
                        noCoachProductList.add(it.giftProduct)
                    }
                    if (it?.coach == null && it?.giftProduct == null && it?.isActive == true) {
                        noCoachProductList.add(
                            GiftProductDto(
                                "",
                                0,
                                0,
                                "",
                                "",
                                "",
                                0,
                                "بدون هدیه",
                                "non-id"
                            )
                        )
                    }
                }
                supportMap[noCoachDto!!] = noCoachProductList
            }
        } else {
            val locationSet = mutableSetOf<TimeAndLocationsDto>()
            movie.detail.timeAndLocation.map {
                if (it != null) {
                    locationSet.add(it)
                }
            }
            locationSet.map { location ->
                val productList = mutableListOf<GiftProductDto>()
                movie.detail.timeAndLocation.map {
                    if (location.location == it?.location && location.time == it?.time && location.id == it?.id) {
                        if (it.giftProduct != null && it.isActive) {
                            productList.add(it.giftProduct)
                        } else {
                            if (it.isActive) {
                                productList.add(
                                    GiftProductDto(
                                        "",
                                        0,
                                        0,
                                        "",
                                        "",
                                        "",
                                        0,
                                        "بدون محصول",
                                        "non-id"
                                    )
                                )
                            }
                        }
                    }
                    locationMap.put(location, productList)
                }
            }
        }
    }

    fun getSelectedVariant(movie: AcademyDto): VariantDto? {
        movie.variants.map {
            if (it?.coach == null && it?.giftProduct?.id == selectedGifts?.id) {
                return it
            } else if (it?.coach == null && it?.giftProduct == null) {
                return it
            } else {
                if (selectedGifts?.id == "non-id") {
                    if (it?.coach?.id == selectedCoach.value?.id && it?.giftProduct == null) {
                        return it
                    }
                } else {
                    if (it?.coach?.id == selectedCoach.value?.id && it?.giftProduct?.id == selectedGifts?.id) return it
                }
            }
        }
        return null
    }

    fun getSelectedLocation(movie: AcademyDto): TimeAndLocationsDto? {
        movie.detail.timeAndLocation.map {
            if (selectedGifts?.id == "non-id") {
                if (it?.id == selectedLocation.value?.id && it?.giftProduct == null) {
                    return it
                }
            } else {
                if (it?.id == selectedLocation.value?.id && it?.giftProduct?.id == selectedGifts?.id) return it
            }
        }
        return null
    }

    fun setSelectedCoach(coachDto: CoachDto) {
        viewModelScope.launch {
            _selectedCoach.emit(coachDto)
        }
    }

    fun setSelectedLocation(timeAndLocationsDto: TimeAndLocationsDto) {
        viewModelScope.launch {
            _selectedLocation.emit(timeAndLocationsDto)
        }
    }

    fun setSelectedGift(giftProductDto: GiftProductDto) {
        viewModelScope.launch {
            _selectedGift.emit(giftProductDto)
            selectedGifts = giftProductDto
        }
    }

    suspend fun addCartItem(item: CartItem) {
        _addingCartFlow.emit("در حال اضافه کردن به سبد خرید.")
        shoppingCartRepository.addCartItem(item).collect {
            when (it) {
                is Resource.Success -> {
                    _addingCartFlow.emit("دوره شما با موفقیت به سبد خرید اضافه شد.")
                }

                is Resource.Failure -> {
                    _addingCartFlow.emit("خطا در انجام عملیات.")
                }

                else -> {
                }
            }
        }
    }

}