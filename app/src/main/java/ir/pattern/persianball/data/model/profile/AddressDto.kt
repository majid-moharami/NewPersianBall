package ir.pattern.persianball.data.model.profile

import com.google.gson.annotations.SerializedName


data class AddressDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val result: List<Address?>
)
data class Address(
    @SerializedName("postal_code") var postalCode: Int?,
    @SerializedName("address") var address: String?,
    @SerializedName("receiver_home_phone") var homePhone: String?,
    @SerializedName("receiver_mobile_phone") var mobilePhone: String?,
    @SerializedName("email") var email: String?,
)


