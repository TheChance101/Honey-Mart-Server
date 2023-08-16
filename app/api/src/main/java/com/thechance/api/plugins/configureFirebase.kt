package com.thechance.api.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.*
import java.io.ByteArrayInputStream

fun Application.configureFirebaseApp(){
    val credentialsJson = System.getenv("firebase_key")
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(ByteArrayInputStream(credentialsJson.toByteArray()))).build()
    FirebaseApp.initializeApp(options)
}