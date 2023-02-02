package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCart
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartDto
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.api.ShoppingCartService
import javax.inject.Inject

class ShoppingCartDataSource
@Inject constructor(
    private val shoppingCartService: ShoppingCartService
) {
    suspend fun addCart(cartItem: CartItem) : Resource<Any?> {
        return Request.getResponse(
            request = {shoppingCartService.addCartItem(cartItem)}
        )
    }

    suspend fun deleteCart(itemId: Int) : Resource<Any?> {
        return Request.getResponse(
            request = {shoppingCartService.deleteCartItem(itemId)}
        )
    }

    suspend fun getShoppingCart() : Resource<ShoppingCart> {
        return Request.getResponse(
            request = {shoppingCartService.getBasket()}
        )
    }
}