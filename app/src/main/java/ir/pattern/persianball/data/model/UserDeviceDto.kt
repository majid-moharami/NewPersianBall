package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class UserDeviceDto(
    @SerializedName("device") var device: Int
)
