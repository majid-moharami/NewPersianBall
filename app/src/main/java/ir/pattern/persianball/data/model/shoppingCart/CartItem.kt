package ir.pattern.persianball.data.model.shoppingCart

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("course") var course: Int? = null,
    @SerializedName("product") var product: Int? = null,
    @SerializedName("quantity") var quantity: Int?
)

data class UpdateCartItemDto(
    @SerializedName("quantity") var quantity: Int?
)

data class UpdateResponse(
    @SerializedName("course") var course: Int?,
    @SerializedName("product") var product: Int?,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("id") var id: Int?
)
