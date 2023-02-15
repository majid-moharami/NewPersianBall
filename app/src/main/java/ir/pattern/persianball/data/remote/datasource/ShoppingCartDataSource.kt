package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.AddressResult
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.model.shoppingCart.*
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.api.ShoppingCartService
import retrofit2.Response
import javax.inject.Inject

class ShoppingCartDataSource
@Inject constructor(
    private val shoppingCartService: ShoppingCartService
) {
    suspend fun addCart(cartItem: CartItem): Resource<Any?> {
        return Request.getResponse(
            request = { shoppingCartService.addCartItem(cartItem) }
        )
    }

    suspend fun deleteCart(itemId: Int): Resource<Any?> {
        return Request.getResponse(
            request = { shoppingCartService.deleteCartItem(itemId) }
        )
    }

    suspend fun getShoppingCart(): Resource<ShoppingCart> {
        return Request.getResponse(
            request = { shoppingCartService.getBasket() }
        )
    }

    suspend fun updateCart(updateParam: UpdateCartItemDto, id: Int): Resource<Any?> {
        return Request.getResponse(
            request = { shoppingCartService.updateCartItem(updateParam, id) }
        )
    }

    suspend fun getAddress(): Resource<AddressResult> {
        return Request.getResponse(
            request = { shoppingCartService.getAddress() }
        )
    }

    suspend fun addAddress(orderAddress: OrderAddress): Resource<Any?> {
        return Request.getResponse(
            request = { shoppingCartService.addAddress(orderAddress) }
        )
    }

    suspend fun doOrder(order: Order): Resource<PaymentLink> {
        return Request.getResponse(
            request = { shoppingCartService.doOrder(order) }
        )
    }

    suspend fun getDiscount(discount: Discount): Resource<DiscountDto>{
        return Request.getResponse(
            request = { shoppingCartService.getDiscount(discount) }
        )
    }
}