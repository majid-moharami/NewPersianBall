package ir.pattern.persianball.data.model.home

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Products(@SerializedName("results") val products: List<Product>): Serializable

data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("name_farsi") val nameFarsi: String,
    @SerializedName("name_english") val nameEnglish: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
    @SerializedName("published") val published: Boolean,
    @SerializedName("sorting_order") val sortingOrder: Int,
    @SerializedName("discount_percentage") val discountPercentage: Int?,
    @SerializedName("price") val price: Int?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("variants") val variants: List<VariantsDto?>?,
    @SerializedName("images") val images: List<String>?,
    @SerializedName("video") val video: String?,
    @SerializedName("video_thumbnail") val videoThumbnail: String?
): Serializable

data class VariantsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("image") val image: String?,
    @SerializedName("price") val price: Int,
    @SerializedName("discount_percentage") val discountPercentage: Int,
    @SerializedName("size") val size: String,
    @SerializedName("count") val count: Int,
    @SerializedName("color") val color: String,
    @SerializedName("color_rgb") val colorRgb: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("product") val product: Int
): Serializable
