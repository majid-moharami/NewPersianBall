package ir.pattern.persianball.data.model.shoppingCart

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("delivery_method") val deliveryMethod : String?,
    @SerializedName("address") val addressId : Int?,
    @SerializedName("discount_code") val discountCode : String? = null,
    @SerializedName("is_browser_view") val isBrowserView : Boolean = true
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
    FREE("free")
}

data class PaymentCompleteListDto(
    @SerializedName("results") val results : List<PaymentCompleteDto>
)

data class PaymentCompleteDto(
    @SerializedName("id") val id : Int,
    @SerializedName("user") val user : Int,
    @SerializedName("tracking_code") val trackingCode : Long,
    @SerializedName("status") val status : String,
    @SerializedName("price") val price : Long
)