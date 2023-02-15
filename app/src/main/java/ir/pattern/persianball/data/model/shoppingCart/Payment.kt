package ir.pattern.persianball.data.model.shoppingCart

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("delivery_method") val deliveryMethod : String,
    @SerializedName("address") val addressId : Int,
    @SerializedName("discount_code") val discountCode : String? = null
)

data class PaymentLink(
    @SerializedName("redirect_url") val url : String
)

data class Discount(
    @SerializedName("code") val discountCode : String
)

data class DiscountDto(
    @SerializedName("discount_percent") val discountPercent : Int
)

enum class DeliveryType(val type: String) {
    POST("post"),
    PEYK("peyk"),
    FREE("free ")
}