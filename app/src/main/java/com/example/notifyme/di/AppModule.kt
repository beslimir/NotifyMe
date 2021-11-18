package com.example.notifyme.di

import android.app.Application
import androidx.room.Room
import com.example.notifyme.feature_notifications.data.local.NotificationDatabase
import com.example.notifyme.feature_notifications.data.repository.NotificationRepositoryImpl
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.example.notifyme.feature_notifications.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotificationDatabase(app: Application) = Room.databaseBuilder(
        app,
        NotificationDatabase::class.java,
        "notification_database"
    ).build()

    @Singleton
    @Provides
    fun provideRepository(db: NotificationDatabase): NotificationRepository = NotificationRepositoryImpl(db.notificationDao)

    @Singleton
    @Provides
    fun provideUseCasesWrapper(repository: NotificationRepository): UseCasesWrapper = UseCasesWrapper(
        getAllNotificationsUseCase = GetAllNotificationsUseCase(repository),
        getAllShownNotificationsUseCase = GetAllShownNotificationsUseCase(repository),
        getNotificationByIdUseCase = GetNotificationByIdUseCase(repository),
        insertNotificationUseCase = InsertNotificationUseCase(repository),
        deleteNotificationUseCase = DeleteNotificationUseCase(repository)
    )


}