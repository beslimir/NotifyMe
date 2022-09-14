package com.example.notifyme.feature_notifications.data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET

interface FirebaseAPI {

    /**
     * No need for token as long as the rules are open for everyone
     * **/
    @GET("/v0/b/notifyme-dbf6a.appspot.com/o/notify_me_default.json?alt=media")
    suspend fun getFirebaseJson(): JsonObject

}