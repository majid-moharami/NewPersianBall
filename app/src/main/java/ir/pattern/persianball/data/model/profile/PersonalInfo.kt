package ir.pattern.persianball.data.model.profile

import com.google.gson.annotations.SerializedName


data class PersonalInformationDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val result: List<PersonalDto?>
)

data class PersonalInfoCreate(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("first_name_latin") val firstNameLatin: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("last_name_latin") val lastNameLatin: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobile_number") val mobileNumber: String?,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("nationality") val nationality: Int?,
    @SerializedName("nation_id") val nationId: Int?,
)

data class PersonalDto(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("first_name_latin") val firstNameLatin: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("last_name_latin") val lastNameLatin: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobile_number") val mobileNumber: String?,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("nationality") val nationality: Int?,
    @SerializedName("nation_id") val nationId: Int?,
    @SerializedName("is_mobile_verified") val isMobileVerified: Boolean,
    @SerializedName("is_email_verified") val isEmailVerified: Boolean,
    @SerializedName("username") val username: String
)