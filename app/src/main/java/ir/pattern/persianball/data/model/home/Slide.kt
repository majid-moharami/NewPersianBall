package ir.pattern.persianball.data.model.home

import com.google.gson.annotations.SerializedName

data class Gallery(@SerializedName("results") val gallery: List<Slide>)

data class Slide(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("image") val image: String,
    @SerializedName("position") val position: Int,
)
