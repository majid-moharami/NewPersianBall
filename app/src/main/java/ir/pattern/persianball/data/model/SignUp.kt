package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class SignUp constructor(
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile_number") var phoneNumber: String? = null,
    @SerializedName("username") var userName: String,
    @SerializedName("password") var password: String
)