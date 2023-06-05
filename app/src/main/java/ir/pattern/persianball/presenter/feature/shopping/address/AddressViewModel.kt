package ir.pattern.persianball.presenter.feature.shopping.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.AddressResult
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.shoppingCart.DeliveryType
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.shopping.address.recycler.OrderAddressData
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartData
import ir.pattern.persianball.utils.PagingCrudFunctions
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderAddressViewModel
@Inject constructor(
    val shoppingCartRepository: ShoppingCartRepository
) : BaseViewModel() {

    private var recyclerList = mutableListOf<RecyclerItem>()
    var isFirst = true
    var lastSelectedAddressId: Int? = null
    private val _addressList = MutableSharedFlow<Resource<AddressResult>>()
    val addressList = _addressList.asSharedFlow()
    var addressAdded = shoppingCartRepository.addressAdded

    init {
        viewModelScope.launch {
            getAddress()
        }
    }

    fun setAddressAdded() {
        viewModelScope.launch {
            shoppingCartRepository.setAddressAdded(false)
        }
    }

    suspend fun getAddress() {
        recyclerList = mutableListOf<RecyclerItem>()
        shoppingCartRepository.getAddress().collect {
            _addressList.emit(it)
            when (it) {
                is Resource.Success -> {
                    _addressList.emit(it)
                    if (lastSelectedAddressId == null) {
                        it.data.addresses.map { addressDto ->
                            recyclerList.add(RecyclerItem(OrderAddressData(addressDto, isFirst)))
                            isFirst = false
                            lastSelectedAddressId = addressDto.id
                        }
                    } else {
                        it.data.addresses.map { addressDto ->
                            recyclerList.add(
                                RecyclerItem(
                                    OrderAddressData(
                                        addressDto, lastSelectedAddressId == addressDto.id
                                    )
                                )
                            )
                        }
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {
                }
            }
        }
    }

    fun updateSelectedAddress(id: Int) {
        execute(PagingCrudFunctions.Edit({ recyclerItems ->
            (recyclerItems.data as? OrderAddressData)?.isDefault == true
        }, { _, rvItem ->
            RecyclerItem((rvItem.data as OrderAddressData).let { item ->
                OrderAddressData(item.addressDto, isDefault = false)
            })
        }))

        execute(PagingCrudFunctions.Edit({ recyclerItems ->
            (recyclerItems.data as? OrderAddressData)?.addressDto?.id == id
        }, { _, rvItem ->
            RecyclerItem((rvItem.data as OrderAddressData).let { item ->
                OrderAddressData(item.addressDto, isDefault = true)
            })
        }))
        lastSelectedAddressId = id
    }
}