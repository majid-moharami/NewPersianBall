package ir.pattern.persianball.data.model.address

import com.google.gson.annotations.SerializedName

data class AddressResult(
    @SerializedName("results") val addresses: List<AddressDto>
)

data class AddressDto(
    @SerializedName("id") val id: Int,
    @SerializedName("address_name") val addressName: String?,
    @SerializedName("user_full_name") val userFullName: String?,
    @SerializedName("postal_code") val postalCode: String,
    @SerializedName("address") val address: String,
    @SerializedName("receiver_home_phone") val receiverHomePhone: String,
    @SerializedName("receiver_mobile_phone") val receiverMobilePhone: String,
    @SerializedName("email") val email: String?,
    @SerializedName("city") var city: String? = null,
    @SerializedName("province") var province: String? = null
)

data class OrderAddress(
    @SerializedName("address_name") val addressName: String?,
    @SerializedName("postal_code") val postalCode: String,
    @SerializedName("address") val address: String,
    @SerializedName("receiver_home_phone") var receiverHomePhone: String,
    @SerializedName("receiver_mobile_phone") val receiverMobilePhone: String,
    @SerializedName("email") var email: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("province") var province: String? = null
)