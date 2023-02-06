package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.SignUp
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCart
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartDto
import ir.pattern.persianball.data.model.shoppingCart.UpdateCartItemDto
import retrofit2.Response
import retrofit2.http.*

interface ShoppingCartService {
    @POST("order/basket-items/")
    suspend fun addCartItem(@Body cartItem: CartItem): Response<Any>

    @GET("order/basket/")
    suspend fun getBasket() : Response<ShoppingCart>

    @DELETE("order/basket-items/{id}/")
    suspend fun deleteCartItem(@Path("id") itemId: Int) : Response<Any>

    @PATCH("order/basket-items/{id}/")
    suspend fun updateCartItem(@Body cartItem: UpdateCartItemDto, @Path("id") itemId: Int): Response<Any>
}