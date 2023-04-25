package ir.pattern.persianball.notification

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.pattern.persianball.utils.SharedPreferenceUtils
import javax.inject.Inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseNotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("NNNN", "plus")
        var c = sharedPreferenceUtils.getNotificationCounter().count
        c += 1
        sharedPreferenceUtils.putNotificationCounter(c)
    }
}