package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCart
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartDto
import ir.pattern.persianball.data.model.shoppingCart.UpdateCartItemDto
import ir.pattern.persianball.data.remote.datasource.ShoppingCartDataSource
import ir.pattern.persianball.data.remote.datasource.UserRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingCartRepository
@Inject
constructor(
    private val shoppingCartDataSource: ShoppingCartDataSource
) {
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


}