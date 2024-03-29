package com.thechance.core.domain.repository

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.entity.*
import com.thechance.core.entity.market.MarketRequest
import java.util.*

interface AuthRepository {

    //region user

    suspend fun createUser(password: String, fullName: String, email: String): Boolean

    suspend fun isEmailExists(email: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByEmail(email: String): User

    fun isUserValidPassword(user: User, password: String): Boolean

    suspend fun getProfile(userId: Long): User

    //endregion


    //region owner
    suspend fun createOwner(fullName: String, email: String, password: String): Boolean

    suspend fun isOwnerEmailExists(email: String): Boolean

    suspend fun getOwnerByEmail(email: String): Owner

    fun isOwnerValidPassword(owner: Owner, password: String): Boolean

    suspend fun isValidOwner(ownerId: Long): Boolean

    suspend fun getOwner(ownerId: Long): Owner
    //endregion

   //region admin
    suspend fun isValidAdmin(password: String, email: String): Boolean
    suspend fun getAdminByEmail(email: String): AdminDetails
    suspend fun getMarketsRequestsDetails(isApproved: Boolean?): List<MarketRequest>
    suspend fun approveMarket(marketId: Long, isApproved: Boolean): Boolean
    //endregion


    fun getTokens(id: Long, role: String): Tokens
    fun verifyTokenSubject(token: String): String
    fun getTokenExpiration(token: String): Date
    fun verifyTokenType(token: String): String
    fun verifyTokenRole(token: String): String
    suspend fun getUser(userId: Long): User

    //region deviceTokens
    suspend fun getUserDeviceTokens(receiverId: Long): List<String>
    suspend fun getOwnerDeviceTokens(receiverId: Long): List<String>
    suspend fun saveUserDeviceTokens(userId: Long, deviceToken: String)
    suspend fun saveOwnerDeviceTokens(ownerId: Long, deviceToken: String)

    //endregion

    //region notification
    suspend fun sendNotification(notification: NotificationRequest): Boolean
    suspend fun saveUserNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean
    suspend fun saveOwnerNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean
    suspend fun getUserNotificationHistory(userId: Long): List<Notification>
    suspend fun getOwnerNotificationHistory(ownerId: Long): List<Notification>
    suspend fun updateUserNotificationState(userId: Long, isRead: Boolean): Boolean
    suspend fun updateOwnerNotificationState(ownerId: Long, isRead: Boolean): Boolean
    //endregion
    suspend fun getMarketIdByOwnerId(ownerId: Long): Long?
}