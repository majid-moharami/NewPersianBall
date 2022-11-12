package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class ChangePassword(
    @SerializedName("mobile_number") var phoneNumber: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String
)
