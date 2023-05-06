package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class VerifyUser(
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile_number") var phoneNumber: String? = null,
    @SerializedName("code") var code: String
)
