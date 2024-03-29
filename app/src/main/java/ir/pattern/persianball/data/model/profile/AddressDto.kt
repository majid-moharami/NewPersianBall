package ir.pattern.persianball.data.model.profile

import com.google.gson.annotations.SerializedName


data class AddressDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val result: List<Address?>
)

data class Address(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("postal_code") var postalCode: Long? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("province") var province: String? = null,
    @SerializedName("receiver_home_phone") var homePhone: String? = null,
    @SerializedName("receiver_mobile_phone") var mobilePhone: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("address_name") var addressName: String? = null
) : java.io.Serializable{
    fun isEmpty(): Boolean =
        postalCode == null && address.isNullOrEmpty() && homePhone.isNullOrEmpty() && mobilePhone.isNullOrEmpty() && email.isNullOrEmpty()
}


