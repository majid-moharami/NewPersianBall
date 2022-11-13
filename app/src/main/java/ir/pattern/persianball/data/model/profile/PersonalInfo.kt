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
    @SerializedName("id") var id: Int? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("first_name_latin") var firstNameLatin: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("last_name_latin") var lastNameLatin: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("mobile_number") var mobileNumber: String? = null,
    @SerializedName("birth_date") var birthDate: Int? = null,
    @SerializedName("avatar") var avatar: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("nationality") var nationality: Int? = null,
    @SerializedName("nation_id") var nationId: Long? = null,
    @SerializedName("is_mobile_verified") var isMobileVerified: Boolean = false,
    @SerializedName("is_email_verified") var isEmailVerified: Boolean = false,
    @SerializedName("username") var username: String = ""
)