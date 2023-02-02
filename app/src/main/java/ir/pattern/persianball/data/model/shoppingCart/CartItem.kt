package ir.pattern.persianball.data.model.shoppingCart

import com.google.gson.annotations.SerializedName

data class CartItem(
    @SerializedName("course") var course: Int? = null,
    @SerializedName("product") var product: Int? = null
)
