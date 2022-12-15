package ir.pattern.persianball.data.model.home

import com.google.gson.annotations.SerializedName

data class Products(@SerializedName("results") val products: List<Product>)

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name_farsi") val nameFarsi: String,
    @SerializedName("name_english") val nameEnglish: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("published") val published: Boolean,
    @SerializedName("discount_percentage") val discountPercentage: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("category") val category: Int
)
