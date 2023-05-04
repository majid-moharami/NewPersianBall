package ir.pattern.persianball.data.model

import com.google.gson.annotations.SerializedName

data class DeviceDto(
    @SerializedName("sdk_version") var sdkVersion: Int,
    @SerializedName("sdk_release") var sdkRelease: String,
    @SerializedName("display") var display: String,
    @SerializedName("manufacturer") var manufacturer: String?,
    @SerializedName("model") var model: String?,
    @SerializedName("version_name") var versionName: String?,
    @SerializedName("version_code") var versionCode: String?,
    @SerializedName("device_id") var deviceId: String,
    @SerializedName("fcm_token") var fcmToken: String,
    @SerializedName("device_type") var deviceType: String,
)