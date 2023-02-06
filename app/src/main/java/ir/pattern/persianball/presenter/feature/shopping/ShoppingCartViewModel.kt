package ir.pattern.persianball.presenter.feature.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartDto
import ir.pattern.persianball.data.model.shoppingCart.UpdateCartItemDto
import ir.pattern.persianball.data.model.store.StoreDto
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.sql.RowId
import javax.inject.Inject

@HiltViewModel
class ShoppingCartViewModel
@Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModel() {
    protected val _recyclerItems = MutableStateFlow<RecyclerData?>(null)
    val recyclerItems: StateFlow<RecyclerData?> = _recyclerItems.asStateFlow()
    private var recyclerList = mutableListOf<RecyclerItem>()
    val shopCart = MutableSharedFlow<ShoppingCartDto>()

    init {
        viewModelScope.launch {
            getShoppingCart()
        }
    }

    private suspend fun getShoppingCart() {
        shoppingCartRepository.getShoppingCart().collect {
            recyclerList = mutableListOf<RecyclerItem>()
            when (it) {
                is Resource.Success -> {
                    shopCart.emit(it.data.result[0])
                    it.data.result[0].items.map { shoppingCartItemDto ->
                        recyclerList.add(
                            RecyclerItem(ShoppingCartData(shoppingCartItemDto))
                        )
                    }
                    _recyclerItems.value = RecyclerData(flowOf(PagingData.from(recyclerList)))
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }

    suspend fun deleteCartItem(itemId: Int){
        shoppingCartRepository.deleteCartItem(itemId).collect{
            when (it) {
                is Resource.Success -> {
                    getShoppingCart()
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }

    suspend fun updateItemQuantity(quantity: Int ,id: Int){
        shoppingCartRepository.updateCartItem(UpdateCartItemDto(quantity), id).collect{
            when (it) {
                is Resource.Success -> {
                    getShoppingCart()
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }
}