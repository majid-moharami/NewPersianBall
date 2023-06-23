package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.AddressResult
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.shoppingCart.*
import ir.pattern.persianball.data.repository.remote.datasource.ShoppingCartDataSource
import ir.pattern.persianball.data.repository.remote.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartRepository
@Inject
constructor(
    private val shoppingCartDataSource: ShoppingCartDataSource
) {

    private var _addressAdded = MutableSharedFlow<Boolean>()
    val addressAdded = _addressAdded.asSharedFlow()
    var totalPrice: Float = 0F
    var isShipping = false
    var basketList = listOf<ShoppingCartItemDto>()

    suspend fun setAddressAdded(boolean: Boolean){
        _addressAdded.emit(boolean)
    }

    suspend fun addCartItem(cartItem: CartItem): Flow<Resource<Any?>> {
        return flow {
            val result = shoppingCartDataSource.addCart(cartItem)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteCartItem(itemId: Int) : Flow<Resource<Any?>>{
        return flow {
            val result = shoppingCartDataSource.deleteCart(itemId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateCartItem(updateParam: UpdateCartItemDto, itemId: Int) : Flow<Resource<Any?>> {
        return flow {
            val result = shoppingCartDataSource.updateCart(updateParam, itemId)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getShoppingCart(): Flow<Resource<ShoppingCart>>{
        return flow {
            val result = shoppingCartDataSource.getShoppingCart()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAddress() : Flow<Resource<AddressResult>>{
        return flow {
            val result = shoppingCartDataSource.getAddress()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun addAddress(orderAddress: OrderAddress) : Flow<Resource<Any?>>{
        return flow {
            val result = shoppingCartDataSource.addAddress(orderAddress)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateAddress(id: Long, orderAddress: OrderAddress): Flow<Resource<Any?>> {
        return flow {
            val result = shoppingCartDataSource.updateAddress(id ,orderAddress)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun doOrder(order: Order): Flow<Resource<PaymentLink?>> {
        return flow {
            val result = shoppingCartDataSource.doOrder(order)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDiscount(discount: Discount) : Flow<Resource<DiscountDto?>> {
        return flow {
            val result = shoppingCartDataSource.getDiscount(discount)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPayments() : Flow<Resource<PaymentCompleteListDto?>> {
        return flow {
            val result = shoppingCartDataSource.getPayments()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}