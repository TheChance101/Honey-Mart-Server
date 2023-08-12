package com.thechance.core.utils


internal const val NORMAL_USER_ROLE = "NormalUser"

internal const val MARKET_OWNER_ROLE = "MarketOwner"

internal const val ORDER_STATUS_PENDING = 1
internal const val ORDER_STATUS_IN_PROGRESS = 2
internal const val ORDER_STATUS_DONE = 3
internal const val ORDER_STATUS_CANCELED_BY_USER = 4
internal const val ORDER_STATUS_CANCELED_BY_OWNER = 5
internal const val ORDER_STATUS_DELETED = 6

const val ROLE_TYPE = "ROLE_TYPE"
const val TOKEN_TYPE = "tokenType"
const val ACCESS_TOKEN = "accessToken"
const val REFRESH_TOKEN = "refreshToken"

const val BASE_URL = "https://honey-mart-server-oe345.ondigitalocean.app"
//const val BASE_URL = "http://0.0.0.0:8080"

const val PRODUCT_IMAGES_PATH = "files/market"
const val MARKET_IMAGES_PATH = "files/markets"
const val USER_IMAGES_PATH = "files/image_uploads"

internal const val PAGE_SIZE = 10
internal const val RECENT_PRODUCT = 20


const val API_KEY_HEADER_NAME = "apiKey"
const val API_SECRET_KEY = "honey_secret_api_key"
const val JWT_AUTHENTICATION = "jwt"
const val API_KEY_AUTHENTICATION = "apiKey"