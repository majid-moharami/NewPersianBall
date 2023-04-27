package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refresh") val refresh: String
)
