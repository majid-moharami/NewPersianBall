package ir.pattern.persianball.data.model.shoppingCart

import com.google.gson.annotations.SerializedName

data class ShoppingCart(
    @SerializedName("results") val result: List<ShoppingCartDto>
)

data class ShoppingCartDto(
    @SerializedName("user") var user: Int,
    @SerializedName("price") var price: PriceDto,
    @SerializedName("items") var items: List<ShoppingCartItemDto>
)

data class PriceDto(
    @SerializedName("total_price") var totalPrice: Float,
    @SerializedName("nat") var nat: Float,
    @SerializedName("discount") var discount: Float,
    @SerializedName("shipping_price") var shippingPrice: Int
)

data class ShoppingCartItemDto(
    @SerializedName("id") var id: Int,
    @SerializedName("product") var product: ShopCartProduct? = null,
    @SerializedName("course") var course: ShopCartCourse? = null,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("created_at") var created_at: String,
    @SerializedName("basket") var basket: Int
)

data class ShopCartProduct(
    @SerializedName("image") var image: String,
    @SerializedName("discount_percentage") var discountPercentage: Int,
    @SerializedName("price") var price: Int,
    @SerializedName("size") var size: String,
    @SerializedName("count") var count: Int,
    @SerializedName("color") var color: String,
    @SerializedName("color_rgb") var color_rgb: String,
    @SerializedName("product_name") var productName: String
)

data class ShopCartCourse(
    @SerializedName("is_active") var is_active: Boolean,
    @SerializedName("image") var image: String,
    @SerializedName("price") var price: Int,
    @SerializedName("location") var location: String,
    @SerializedName("time") var time: String,
    @SerializedName("coach") var coach: Coach,
    @SerializedName("course_name") var courseName: String,
    @SerializedName("shipment_required") var shipmentRequired: Boolean
)

data class Coach(
    @SerializedName("id") var id: Int,
    @SerializedName("full_name") var full_name: String,
    @SerializedName("avatar") var avatar: String
)