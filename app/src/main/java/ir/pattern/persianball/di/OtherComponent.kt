package ir.pattern.persianball.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pattern.persianball.notification.FirebaseNotificationService
import ir.pattern.persianball.notification.NotificationReceiver

@Module
@InstallIn(SingletonComponent::class)
interface OtherComponent {
}