package ir.pattern.persianball.data.model.home

import com.google.gson.annotations.SerializedName

data class Gallery(@SerializedName("results") val gallery: List<Slide>)

data class Slide(
    @SerializedName("uuid") val uuid: String,
    @SerializedName("image") val image: String,
    @SerializedName("position") val position: Int,
)


data class SliderList(@SerializedName("results") val slider: List<Slider>)

data class Slider(
    @SerializedName("image") val image: String?,
    @SerializedName("click_url") val url: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("position") val position: Int?,
    @SerializedName("category") val category: Int?,
    @SerializedName("unique_id") val uniqueId: Int?,
    @SerializedName("slider_type") val sliderType: String
)
