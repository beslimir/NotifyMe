package com.example.notifyme.feature_notifications.domain.use_cases

import com.example.notifyme.feature_notifications.domain.repository.NotificationRepository
import com.google.gson.JsonObject

class GetJsonFromFirebaseUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): JsonObject {
        return notificationRepository.getFirebaseJson()
    }
}