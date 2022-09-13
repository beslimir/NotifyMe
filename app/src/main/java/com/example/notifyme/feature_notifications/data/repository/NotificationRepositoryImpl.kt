package com.example.notifyme.feature_notifications.data.repository

import androidx.compose.ui.graphics.toArgb
import com.example.notifyme.feature_notifications.data.local.NotificationDao
import com.example.notifyme.feature_notifications.data.local.NotificationItemEntity
import com.example.notifyme.feature_notifications.data.remote.FirebaseAPI
import com.example.notifyme.feature_notifications.domain.model.NotificationDetails
import com.example.notifyme.feature_notifications.domain.model.NotificationItem
import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * getAllNotifications() is a Flow (non-suspend) - map twice; return
 * getAllShownNotifications is a List (suspend) - map once; flow - emit
 *
 * **/

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao,
    private val firebaseAPI: FirebaseAPI
) : NotificationRepository, FirebaseAPI {

    override fun getAllNotifications(): Flow<List<NotificationItem>> {
        val notifications = notificationDao.getAllNotifications().map { list ->
            list.map { entity ->
                entity.toNotificationItem()
            }
        }

        return notifications
    }

    override fun getAllNotificationsUntilDate(date: Long): Flow<List<NotificationItem>> {
        val notifications = notificationDao.getAllNotificationsUntilDate(date).map { list ->
            list.map { entity ->
                entity.toNotificationItem()
            }
        }

        return notifications
    }

    override suspend fun getNotificationById(id: Int): NotificationItem =
        notificationDao.getNotificationById(id).toNotificationItem()

    override suspend fun getNotificationByDate(date: Long): NotificationItem {
        return try {
            notificationDao.getNotificationByDate(date).toNotificationItem()
        } catch (e: NullPointerException) {
            //temporary solution...
            getLastNotification()
        }
    }

    override suspend fun insertNotification(notificationItem: NotificationItem) {
        notificationDao.insertNotification(notificationItem.toNotificationItemEntity())
    }

    override suspend fun updateDateToAllNotifications(date: Long) {
        notificationDao.updateDateToAllNotifications(date)
    }

    override suspend fun deleteNotification(notificationItem: NotificationItem) {
        notificationDao.deleteNotification(notificationItem.toNotificationItemEntity())
    }

    /**
     * This method is used with an direct link to the firebase storage,
     * because the downloadUrl way isn't working...
     * Usually, the downloadURL should be obtained with the usual way (reference, child),
     * but for some reason it took too long...
     * **/

    override suspend fun getFirebaseJson(): JsonObject {
        return try {
            val response = firebaseAPI.getFirebaseJson()
            response
        } catch (e: Exception) {
            JsonObject()
        }
    }

//    @Suppress("BlockingMethodInNonBlockingContext")
//    override suspend fun getJsonFromFirebase(): String {
//        val jsonRef = Firebase.storage.reference
//        var finalJson = ""
//        var job: Job? = null
//        try {
//            job = CoroutineScope(Dispatchers.IO).launch {
//                val downloadURL = jsonRef.child("test.json").downloadUrl.await()
//                try {
//                    val url = URL(downloadURL.toString())
//                    val input = BufferedReader(InputStreamReader(url.openStream()))
//                    var str: String?
//                    while (input.readLine().also { str = it } != null) {
//                        finalJson = finalJson.plus(str)
//                    }
//                    input.close()
//                } catch (e: Exception) {
//                    Log.d(Constants.TAG, "MY JSON: ${e.message}")
//                }
//            }
//            job.join()
//        } catch (e: Exception) {
//
//        }
//
//        return finalJson
//    }

    private fun getLastNotification() = NotificationItem(
        content = "This is the last notification",
        details = listOf(
            NotificationDetails(
                author = "beslimir",
                dedicated_to = "a special person",
                icon_type = "fire",
                type = "motivational"
            )
        ),
        id = 9999,
        title = "Title of the last notification",
        date = 4133890800000L,
        color = NotificationItemEntity.notificationItemColors.random().toArgb()
    )
}