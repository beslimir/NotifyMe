package com.example.notifyme.di

import android.app.AlarmManager
import android.app.Application
import android.content.Context.ALARM_SERVICE
import androidx.room.Room
import com.example.notifyme.feature_notifications.data.local.Converters
import com.example.notifyme.feature_notifications.data.local.NotificationDatabase
import com.example.notifyme.feature_notifications.data.local.PrefsManager
import com.example.notifyme.feature_notifications.data.remote.FirebaseAPI
import com.example.notifyme.feature_notifications.data.repository.NotificationRepositoryImpl
import com.example.notifyme.feature_notifications.data.util.GsonParser
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.example.notifyme.feature_notifications.domain.use_cases.*
import com.example.notifyme.feature_notifications.util.Constants.API_BASE_URL
import com.example.notifyme.feature_notifications.util.NotificationUtil
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    ).addTypeConverter(Converters(GsonParser(Gson())))
        .build()

    @Singleton
    @Provides
    fun provideRepository(
        db: NotificationDatabase,
        firebaseAPI: FirebaseAPI,
    ): NotificationRepository = NotificationRepositoryImpl(
        notificationDao = db.notificationDao,
        firebaseAPI = firebaseAPI
    )

    @Singleton
    @Provides
    fun provideUseCasesWrapper(
        repository: NotificationRepository,
    ): UseCasesWrapper = UseCasesWrapper(
        getAllNotificationsUseCase = GetAllNotificationsUseCase(repository),
        getAllNotificationsUntilDateUseCase = GetAllNotificationsUntilDateUseCase(repository),
        getNotificationByIdUseCase = GetNotificationByIdUseCase(repository),
        getNotificationByDateUseCase = GetNotificationByDateUseCase(repository),
        insertNotificationUseCase = InsertNotificationUseCase(repository),
        updateDateToAllNotificationsUseCase = UpdateDateToAllNotificationsUseCase(repository),
        deleteNotificationUseCase = DeleteNotificationUseCase(repository),
        getJsonFromFirebaseUseCase = GetJsonFromFirebaseUseCase(repository)
    )

    @Singleton
    @Provides
    fun provideTestApi(): FirebaseAPI {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FirebaseAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideAlarmManager(app: Application) = app.getSystemService(ALARM_SERVICE) as AlarmManager

    @Singleton
    @Provides
    fun providePrefsManager(app: Application) = PrefsManager(app)

    @Singleton
    @Provides
    fun provideNotificationUtil(
        prefsManager: PrefsManager,
        alarmManager: AlarmManager,
        app: Application,
        useCases: UseCasesWrapper,
    ) = NotificationUtil(prefsManager, alarmManager, app, useCases)
}