package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("refresh") val refresh: String?,
    @SerializedName("access") val access: String?
)
