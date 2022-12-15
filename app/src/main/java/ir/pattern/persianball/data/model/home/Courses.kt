package ir.pattern.persianball.data.model.home

import com.google.gson.annotations.SerializedName

data class Courses(@SerializedName("results") val courses: List<Course>)

data class Course(
    @SerializedName("id") val id: Int,
    @SerializedName("section_count") val sectionCount: Int,
    @SerializedName("week_count") val weekCount: Int,
    @SerializedName("course_title") val courseTitle: String,
    @SerializedName("image") val image: String,
    @SerializedName("course_description") val courseDescription: String,
    @SerializedName("course_difficulty") val courseDifficulty: String,
    @SerializedName("course_duration") val courseDuration: String
)