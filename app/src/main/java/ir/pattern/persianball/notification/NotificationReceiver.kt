package ir.pattern.persianball.notification

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import ir.pattern.persianball.utils.SharedPreferenceUtils
import javax.inject.Inject

class NotificationReceiver
@Inject constructor() : BroadcastReceiver() {
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    override fun onReceive(context: Context?, intent: Intent?) {
        sharedPreferenceUtils = SharedPreferenceUtils(context as Application)
        val message = intent?.getStringExtra("MESSAGE")
        Log.d("MMMM", "received")
        if (message != null) {
            Log.d("MMMM", "plus")
            var c = sharedPreferenceUtils.getNotificationCounter().count
            c += 1
            sharedPreferenceUtils.putNotificationCounter(c)
        }
    }
}