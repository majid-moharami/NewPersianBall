package ir.pattern.persianball.presenter.feature.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.AddressResult
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.shoppingCart.*
import ir.pattern.persianball.data.model.store.StoreDto
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import ir.pattern.persianball.utils.PagingCrudFunctions
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.RowId
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel
@Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) : BaseViewModel() {
    private var recyclerList = mutableListOf<RecyclerItem>()
    val shopCart = MutableSharedFlow<ShoppingCartDto>()
    private val _payment = MutableSharedFlow<Resource<PaymentLink?>>()
    val payment = _payment.asSharedFlow()
    private val _discount = MutableSharedFlow<Resource<DiscountDto?>>()
    val discount = _discount.asSharedFlow()
    var discountPercent: String? = null

    private val _listRequest = MutableSharedFlow<Resource<ShoppingCart>>()
    val listRequest = _listRequest.asSharedFlow()

    private val _cartList = MutableSharedFlow<Resource<ShoppingCart>>()
    val cartList = _cartList.asSharedFlow()

    init {
        viewModelScope.launch {
            _listRequest.emit(Resource.Loading())
            getShoppingCart()
        }
    }

    suspend fun getShoppingCart() {
        shoppingCartRepository.getShoppingCart().collect {
            recyclerList = mutableListOf<RecyclerItem>()
            _cartList.emit(it)
            when (it) {
                is Resource.Success -> {
                    _listRequest.emit(it)
                    if (!it.data.result.isEmpty()) {
                        shopCart.emit(it.data.result[0])
                        shoppingCartRepository.totalPrice = it.data.result[0].price.totalPrice
                        it.data.result[0].items.map { shoppingCartItemDto ->
                            recyclerList.add(
                                RecyclerItem(ShoppingCartData(shoppingCartItemDto))
                            )
                        }
                        _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
                    }
                }
                is Resource.Failure -> {
                    _listRequest.emit(Resource.Failure(it.error))
                }
                else -> {
                    _listRequest.emit(Resource.Loading())
                }
            }
        }
    }

    suspend fun deleteCartItem(itemId: Int) {
        shoppingCartRepository.deleteCartItem(itemId).collect {
            when (it) {
                is Resource.Success -> {
                    execute(PagingCrudFunctions.Remove { recyclerItem ->
                        (recyclerItem.data as? ShoppingCartData)?.shoppingCartItemDto?.id == itemId
                    })
                    getUpdatedShopping()
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }

    suspend fun updateItemQuantity(quantity: Int, id: Int) {
        shoppingCartRepository.updateCartItem(UpdateCartItemDto(quantity), id).collect {
            when (it) {
                is Resource.Success -> {
                    execute(PagingCrudFunctions.Edit({ recyclerItems ->
                        (recyclerItems.data as? ShoppingCartData)?.shoppingCartItemDto?.id == id
                    }, { _, rvItem ->
                        RecyclerItem((rvItem.data as ShoppingCartData).let { item ->
                            ShoppingCartData(item.shoppingCartItemDto.apply {
                                this.quantity = quantity
                            })
                        })
                    }))
                    getUpdatedShopping()
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }

    suspend fun getUpdatedShopping() {
        shoppingCartRepository.getShoppingCart().collect {
            recyclerList = mutableListOf<RecyclerItem>()
            when (it) {
                is Resource.Success -> {
                    shopCart.emit(it.data.result[0])
                    shoppingCartRepository.totalPrice = it.data.result[0].price.totalPrice
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }

    suspend fun doOrder(order: Order) {
        shoppingCartRepository.doOrder(order).collect {
            _payment.emit(it)
        }
    }

    suspend fun getDiscount(discount: Discount, code: String) {
        shoppingCartRepository.getDiscount(discount).collect {
            when (it) {
                is Resource.Success -> {
                    it.data?.also { percent ->
                        _discount.emit(it)
                        discountPercent = code
                    }
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }
}