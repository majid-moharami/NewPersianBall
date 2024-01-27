package ir.pattern.persianball.data.model.academy

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Academy(
    @SerializedName("results") val result: List<AcademyHomeDto>
)

data class AcademyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("section_count") val section_count: Int,
    @SerializedName("week_count") val weekCount: Int,
    @SerializedName("course_title") val courseTitle: String,
    @SerializedName("image") val image: String?,
    @SerializedName("course_description") val courseDescription: String,
    @SerializedName("course_difficulty") val courseDifficulty: String,
    @SerializedName("course_duration") val courseDuration: Int,
    @SerializedName("detail") val detail: MovieDetailDto,
    @SerializedName("variants") val variants: List<VariantDto?>,
    @SerializedName("discount_percentage") val discountPercentage: Int?,
    @SerializedName("course_price") val coursePrice: Int?,
    @SerializedName("images") val images: List<String?>,
    @SerializedName("video") val video: String?,
    @SerializedName("video_thumbnail") val videoThumbnail: String?,
    @SerializedName("category") val category: CategoryDto?,
    @SerializedName("course_extra_file") val extraFile: String?
): Serializable

data class AcademyHomeDto(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String?,
    @SerializedName("course_price") val coursePrice: Int?,
    @SerializedName("discount_percentage") val discountPercentage: Int?,
    @SerializedName("name_farsi") val nameFarsi: String,
    @SerializedName("name_english") val nameEnglish: String,
    @SerializedName("description") val description: String,
    @SerializedName("published") val published: Boolean,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("pre_requirement") val preRequirement: String,
    @SerializedName("sorting_order") val sortingOrder: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("category") val category: Int,
    @SerializedName("video_thumbnail") val videoThumbnail: String?,
    @SerializedName("video") val video: Int,
    @SerializedName("images") val images: List<String?>,
    @SerializedName("course_extra_file") val courseExtraFile: String?
): Serializable

data class MovieDetailDto(
    @SerializedName("pre_requirement") val preRequirement: String,
    @SerializedName("video_count") val videoCount: Int,
    @SerializedName("length") val length: Int,
    @SerializedName("sections") val sections: List<SectionsDto?>,
    @SerializedName("time_and_location") val timeAndLocation: List<TimeAndLocationsDto?>
): Serializable

data class SectionsDto(
    @SerializedName("week_name") val weekName: String,
    @SerializedName("week_number") val weekNumber: String,
    @SerializedName("sections") val sections: List<SectionDto>
): Serializable

data class SectionDto(
    @SerializedName("title") val title: String,
    @SerializedName("is_locked") val isLocked: Boolean,
    @SerializedName("video_url") val videoUrl: String?,
    @SerializedName("video_thumbnail") val videoThumbnail: String?
): Serializable

data class TimeAndLocationsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("image") val image: String?,
    @SerializedName("price") val price: Int,
    @SerializedName("discount_percentage") val discountPercentage: Int?,
    @SerializedName("location") val location: String?,
    @SerializedName("time") val time: String?,
    @SerializedName("coach") val coach: CoachDto?,
    @SerializedName("course_name") val courseName: String,
    @SerializedName("gift_product") val giftProduct: GiftProductDto?
): Serializable

data class VariantDto(
    @SerializedName("id") val id: Int,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Int,
    @SerializedName("discount_percentage") val discountPercentage: Int?,
    @SerializedName("location") val location: String,
    @SerializedName("time") val time: String,
    @SerializedName("coach") val coach: CoachDto?,
    @SerializedName("course_name") val courseName: String,
    @SerializedName("gift_product") val giftProduct: GiftProductDto?
): Serializable

data class CoachDto(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("avatar") val avatar: String
): Serializable

data class CategoryDto(
    @SerializedName("name_farsi") val nameFarsi: String?,
    @SerializedName("image") val image: String?,
): Serializable

data class GiftProductDto(
    @SerializedName("image") val image: String?,
    @SerializedName("discount_percentage") val discountPercentage: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("size") val size: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("color_rgb") val colorRgb: String?,
    @SerializedName("count") val count: Int,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("id") val id: String?
)