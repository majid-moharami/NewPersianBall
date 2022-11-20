package ir.pattern.persianball.utils

import android.app.Application
import android.content.Context
import android.util.Log
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.model.UserCredential
import javax.inject.Inject

class SharedPreferenceUtils
@Inject
constructor(
    val context: Application
){

    fun getUserCredentials(): UserCredential {
        val sharedPref = context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE)
        // keys
        val usernameKey = context.getString(R.string.share_pref_username)
        val passwordKey = context.getString(R.string.share_pref_password)
        val accessTokenKey = context.getString(R.string.share_pref_token)
        val refreshTokenKey = context.getString(R.string.share_pref_refresh_token)

        return UserCredential(
            sharedPref.getString(usernameKey, "").toString(),
            sharedPref.getString(passwordKey, "").toString(),
            sharedPref.getString(accessTokenKey, "").toString(),
            sharedPref.getString(refreshTokenKey, "").toString()
        )
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

        editor.putString(usernameKey, user.userName)
        editor.putString(passwordKey, user.password)
        editor.putString(accessTokenKey, user.token)
        editor.putString(refreshTokenKey, user.refreshToken)

        editor.apply()
    }

    fun updatePassword(password: String){
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val passwordKey = context.getString(R.string.share_pref_password)
        editor.putString(passwordKey, password)
        editor.apply()
    }

    fun updateAccessToken(token: String?){
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val accessTokenKey = context.getString(R.string.share_pref_token)
        editor.putString(accessTokenKey, token)
        editor.apply()
    }

    fun updateUsername(username: String?){
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        val usernameKey = context.getString(R.string.share_pref_username)
        editor.putString(usernameKey, username)
        editor.apply()
    }

    fun clearCredentials() {
        val editor =
            context.getSharedPreferences(USER_CREDENTIALS_FILE, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        const val TAG = "Utils.SharedPreference"
        // User Authentication
        const val USER_CREDENTIALS_FILE = "UserCredentials"
    }
}