package ir.pattern.persianball.data.model.academy

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Academy(
    @SerializedName("results") val result: List<AcademyDto>
)

data class AcademyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("section_count") val section_count: Int,
    @SerializedName("week_count") val weekCount: Int,
    @SerializedName("course_title") val courseTitle: String,
    @SerializedName("image") val image: String,
    @SerializedName("course_description") val courseDescription: String,
    @SerializedName("course_difficulty") val courseDifficulty: String,
    @SerializedName("course_duration") val courseDuration: Int,
    @SerializedName("detail") val detail: MovieDetailDto,
    @SerializedName("variants") val variants: List<VariantDto?>
): Serializable

data class MovieDetailDto(
    @SerializedName("pre_requirement") val preRequirement: String,
    @SerializedName("video_count") val videoCount: Int,
    @SerializedName("length") val length: Int,
    @SerializedName("sections") val sections: List<SectionsDto?>,
    @SerializedName("time_and_location") val timeAndLocation: List<TimeAndLocationsDto?>
): Serializable

data class SectionsDto(
    @SerializedName("week_number") val weekNumber: String,
    @SerializedName("sections") val sections: List<SectionDto?>
): Serializable

data class SectionDto(
    @SerializedName("title") val title: String,
    @SerializedName("is_locked") val isLocked: Boolean,
    @SerializedName("video_url") val videoUrl: String?
): Serializable

data class TimeAndLocationsDto(
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Int,
    @SerializedName("location") val location: String,
    @SerializedName("time") val time: String,
    @SerializedName("coach") val coach: CoachDto
): Serializable

data class VariantDto(
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("image") val image: String,
    @SerializedName("price") val price: Int,
    @SerializedName("location") val location: String,
    @SerializedName("time") val time: String,
    @SerializedName("coach") val coach: CoachDto
): Serializable

data class CoachDto(
    @SerializedName("id") val id: Int,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("avatar") val avatar: String
): Serializable