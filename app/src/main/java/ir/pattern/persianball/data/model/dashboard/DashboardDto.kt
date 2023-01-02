package ir.pattern.persianball.data.model.dashboard

import com.google.gson.annotations.SerializedName

data class DashboardsDto(
    @SerializedName("results") val results: List<DashboardDto>
)

data class DashboardDto(
    @SerializedName("course_title") val courseTitle: String,
    @SerializedName("course_thumbnail") val courseThumbnail: String,
    @SerializedName("course_description") val courseDescription: String,
    @SerializedName("section_count") val sectionCount: Int,
    @SerializedName("completion_percent") val completionPercent: Int,
    @SerializedName("coach") val coach: String,
    @SerializedName("week_count") val weekCount: Int,
)