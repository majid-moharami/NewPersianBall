package ir.pattern.persianball.presenter

import android.app.Application
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import ir.pattern.persianball.di.ViewComponent


@HiltAndroidApp
class PersianBallApplication : Application() {

    companion object{
        private var singleton: PersianBallApplication? = null
        fun getInstance(): PersianBallApplication? {
            return singleton
        }
    }
    override fun onCreate() {
        singleton = this
        super.onCreate()
    }


    fun viewComponent(): ViewComponent {
        return EntryPoints.get(this, ViewComponent::class.java)
    }
}