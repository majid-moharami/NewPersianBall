package ir.pattern.persianball.utils

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.android.datatransport.cct.internal.LogResponse.fromJson
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.NotificationCounter
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.model.UserCredential
import javax.inject.Inject

class SharedPreferenceUtils
@Inject
constructor(
    val context: Application
) {

    fun getNotificationCounter(): NotificationCounter {
        val sharedPref = context.getSharedPreferences(NOTIFICATION_COUNTER, Context.MODE_PRIVATE)
        val counterKey = context.getString(R.string.notification_counter)
        return NotificationCounter(sharedPref.getInt(counterKey, 0))
    }

    fun getUserCredentials(): UserCredential {
        val sharedPref = context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE)
        // keys
        val usernameKey = context.getString(R.string.share_pref_username)
        val passwordKey = context.getString(R.string.share_pref_password)
        val accessTokenKey = context.getString(R.string.share_pref_token)
        val refreshTokenKey = context.getString(R.string.share_pref_refresh_token)
        val profileImageUrl = context.getString(R.string.share_pref_profile_image_url)

        return UserCredential(
            sharedPref.getString(usernameKey, "").toString(),
            sharedPref.getString(passwordKey, "").toString(),
            sharedPref.getString(accessTokenKey, "").toString(),
            sharedPref.getString(refreshTokenKey, "").toString(),
            sharedPref.getString(profileImageUrl, "").toString()
        )
    }

    fun putNotificationCounter(count: Int) {
        val editor = context.getSharedPreferences(NOTIFICATION_COUNTER, Context.MODE_PRIVATE).edit()
        val counterKey = context.getString(R.string.notification_counter)
        editor.putInt(counterKey, count)

        editor.apply()
    }

    fun putUserCredentials(user: User) {
        Log.d(TAG, "putUserCredentials: $user")

        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()

        // keys
        val usernameKey = context.getString(R.string.share_pref_username)
        val passwordKey = context.getString(R.string.share_pref_password)
        val accessTokenKey = context.getString(R.string.share_pref_token)
        val refreshTokenKey = context.getString(R.string.share_pref_refresh_token)
        val profileImageUrl = context.getString(R.string.share_pref_profile_image_url)

        editor.putString(usernameKey, user.userName)
        editor.putString(passwordKey, user.password)
        editor.putString(accessTokenKey, user.token)
        editor.putString(refreshTokenKey, user.refreshToken)
        editor.putString(profileImageUrl, user.profileImageUrl)

        editor.apply()
    }

    fun updatePassword(password: String) {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val passwordKey = context.getString(R.string.share_pref_password)
        editor.putString(passwordKey, password)
        editor.apply()
    }

    fun updateAccessToken(token: String?) {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val accessTokenKey = context.getString(R.string.share_pref_token)
        editor.putString(accessTokenKey, token)
        editor.apply()
    }

    fun updateUsername(username: String?) {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val usernameKey = context.getString(R.string.share_pref_username)
        editor.putString(usernameKey, username)
        editor.apply()
    }

    fun updateProfileImage(url: String?) {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val profileImageUrl = context.getString(R.string.share_pref_profile_image_url)
        editor.putString(profileImageUrl, "https://api.persianball.ir/media/$url")
        editor.apply()
    }

    fun clearCredentials() {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    fun saveMessagesId(list: List<Int>) {
        val editor = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE).edit()
        val jsonString = Gson().toJson(list)
        editor.putString("my_list", jsonString)
        editor.apply()
    }

    fun getMessagesId() : List<Int> {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("my_list", null)
        val myList = Gson().fromJson<List<Int>>(jsonString, object : TypeToken<List<Int>>() {}.type)
        return if (myList.isNullOrEmpty()) listOf() else myList
    }


    companion object {
        const val TAG = "Utils.SharedPreference"

        // User Authentication
        const val USER_CREDENTIALS_FILE = "UserCredentials"
        const val NOTIFICATION_COUNTER = "NotificationCounter"
    }
}