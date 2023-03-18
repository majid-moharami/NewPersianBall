package ir.pattern.persianball.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import ir.pattern.persianball.views.TryAgainView

@InstallIn(SingletonComponent::class)
@EntryPoint
interface ViewComponent{
//    fun inject(tryAgainView: TryAgainView)
}