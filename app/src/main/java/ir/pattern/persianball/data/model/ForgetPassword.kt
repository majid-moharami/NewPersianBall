package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class ForgetPassword(
    @SerializedName("mobile_number") var phoneNumber: String? = null,
    @SerializedName("email") var email: String? = null
)
