package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCart
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartDto
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
    suspend fun addCartItem(cartItem: CartItem): Resource<Any?> =
        shoppingCartDataSource.addCart(cartItem)

    suspend fun deleteCartItem(itemId: Int) : Resource<Any?> = shoppingCartDataSource.deleteCart(itemId)

    suspend fun getShoppingCart(): Flow<Resource<ShoppingCart>>{
        return flow {
            val result = shoppingCartDataSource.getShoppingCart()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}