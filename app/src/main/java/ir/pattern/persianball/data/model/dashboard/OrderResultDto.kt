package ir.pattern.persianball.data.model.dashboard

import com.google.gson.annotations.SerializedName

class OrderResultDto (
    @SerializedName("results") val results: List<OrderDto>
)

class OrderDto(
    @SerializedName("delivery_method") val deliveryMethod: String,
    @SerializedName("address") val address: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("tracking_code") val trackingCode: String,
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("price") val price: OrderPriceDto
)

class OrderPriceDto(
    @SerializedName("price") val price: Long,
    @SerializedName("nat") val nat: Long,
    @SerializedName("discount") val discount: Long,
    @SerializedName("shipping_price") val shippingPrice: Long
)