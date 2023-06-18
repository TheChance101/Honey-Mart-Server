package com.thechance.api.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

//fun Application.configureCaching() {
//    routing {
//        install(CachingHeaders) {
//            options { call, content -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 1800)) }
//        }
//    }
//}


/////////////

//fun Application.module() {
//    routing {
//        install(CachingHeaders) {
//            options { call, content ->
//                when (content.contentType?.withoutParameters()) {
//                    ContentType.Text.Plain -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
//                    ContentType.Text.Html -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60))
//                    else -> null
//                }
//            }
//        }
//        route("/index") {
//            install(CachingHeaders) {
//                options { call, content -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 1800)) }
//            }
//            get {
//                call.respondText("Index page")
//            }
//        }
//        route("/about") {
//            get {
//                call.respondHtml {
//                    body { p { +"About page" } }
//                }
//            }
//        }
//        route("/profile") {
//            get {
//                val userLoggedIn = true
//                if(userLoggedIn) {
//                    call.caching = CachingOptions(CacheControl.NoStore(CacheControl.Visibility.Private))
//                    call.respondText("Profile page")
//                } else {
//                    call.caching = CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 900))
//                    call.respondText("Login page")
//                }
//            }
//        }
//    }
//}