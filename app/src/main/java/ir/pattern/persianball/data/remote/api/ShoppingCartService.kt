package ir.pattern.persianball.data.remote.api

import ir.pattern.persianball.data.model.SignUp
import ir.pattern.persianball.data.model.address.AddressDto
import ir.pattern.persianball.data.model.address.AddressResult
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.model.shoppingCart.*
import retrofit2.Response
import retrofit2.http.*

interface ShoppingCartService {
    @POST("order/basket-items/")
    suspend fun addCartItem(@Body cartItem: CartItem): Response<Any?>

    @GET("order/basket/")
    suspend fun getBasket() : Response<ShoppingCart>

    @DELETE("order/basket-items/{id}/")
    suspend fun deleteCartItem(@Path("id") itemId: Int) : Response<Any>

    @PATCH("order/basket-items/{id}/")
    suspend fun updateCartItem(@Body cartItem: UpdateCartItemDto, @Path("id") itemId: Int): Response<Any>

    @GET("order/address/")
    suspend fun getAddress() : Response<AddressResult>

    @POST("order/address/")
    suspend fun addAddress(@Body orderAddress: OrderAddress): Response<Any?>

    @POST("order/order/")
    suspend fun doOrder(@Body order: Order) : Response<PaymentLink>

    @POST("payment/voucher/verify/")
    suspend fun getDiscount(@Body discount: Discount) : Response<DiscountDto>
}