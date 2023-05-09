package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class UserMessagesDto(
    @SerializedName("results") val results: List<MessageDto>
)

data class MessageDto(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("text") var text: String
)


