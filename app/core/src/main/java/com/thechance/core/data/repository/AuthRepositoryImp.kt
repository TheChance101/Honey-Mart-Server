package com.thechance.core.data.repository

import com.thechance.core.data.repository.dataSource.*
import com.thechance.core.data.repository.security.HashingService
import com.thechance.core.data.repository.security.TokenService
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.security.token.TokenClaim
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.Tokens
import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.entity.*
import com.thechance.core.entity.market.MarketRequest
import com.thechance.core.utils.ROLE_TYPE
import org.koin.core.component.KoinComponent
import java.util.*

class AuthRepositoryImp(
    private val userDataSource: UserDataSource,
    private val ownerDataSource: OwnerDataSource,
    private val adminDataSource: AdminDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val tokenConfig: TokenConfig,
    private val deviceTokenDataSource: DeviceTokenDataSource,
    private val notificationDataSource: NotificationDataSource,
    ) : AuthRepository, KoinComponent {

    //region user
    override suspend fun createUser(password: String, fullName: String, email: String): Boolean {
        val saltedHash = hashingService.generateSaltedHash(password)
        return userDataSource.createUser(saltedHash, fullName, email)
    }

    override suspend fun isEmailExists(email: String): Boolean = userDataSource.isEmailExists(email)

    override suspend fun isUserExist(userId: Long): Boolean = userDataSource.isUserExist(userId)


    override suspend fun getUserByEmail(email: String): User = userDataSource.getUserByEmail(email)

    override suspend fun getUser(userId: Long): User = userDataSource.getUser(userId)


    override fun isUserValidPassword(user: User, password: String) = hashingService.verify(
        value = password,
        saltedHash = SaltedHash(hash = user.password, salt = user.salt)
    )

    override suspend fun getProfile(userId: Long): User = userDataSource.getProfile(userId)

    //endregion

    //region owner
    override suspend fun createOwner(fullName: String, email: String, password: String): Boolean {
        val saltedHash = hashingService.generateSaltedHash(password)
        return ownerDataSource.createOwner(fullName = fullName, email = email, password = password, saltedHash)
    }

    override suspend fun isOwnerEmailExists(email: String): Boolean = ownerDataSource.isOwnerEmailExists(email)

    override suspend fun getMarketOwnerByEmail(email: String): Owner = ownerDataSource.getOwnerByEmail(email)


    override fun isOwnerValidPassword(owner: Owner, password: String): Boolean = hashingService.verify(
        value = password, saltedHash = SaltedHash(hash = owner.password, salt = owner.salt)
    )

    override suspend fun isValidOwner(ownerId: Long): Boolean = ownerDataSource.isValidOwner(ownerId)

    override suspend fun getOwner(ownerId: Long): Owner = ownerDataSource.getOwner(ownerId)
    //endregion

    //region admin
    override suspend fun isValidAdmin( password: String, email: String): Boolean {
        return adminDataSource.isValidAdmin(password,email)
    }

    override suspend fun getAdminByEmail(email: String): Admin = adminDataSource.getAdminByEmail(email)

    override suspend fun getUnApprovedMarkets(): List<MarketRequest> {
        return adminDataSource.getUnApprovedMarketsDetails()
    }

    override suspend fun approveMarket(marketId: Long, isApproved: Boolean): Boolean  {
       return adminDataSource.approveMarket(marketId,isApproved)
    }
    //endregion

    //region token
    override fun getTokens(id: Long, role: String): Tokens {
        return tokenService.generateTokens(
            config = tokenConfig,
            subject = id.toString(),
            TokenClaim(name = ROLE_TYPE, value = role)
        )
    }

    override fun verifyTokenSubject(token: String): String {
        return tokenService.verifyTokenSubject(token)
    }

    override fun getTokenExpiration(token: String): Date {
        return tokenService.getTokenExpiration(token)
    }

    override fun verifyTokenType(token: String): String {
        return tokenService.verifyTokenType(token)
    }

    override fun verifyTokenRole(token: String): String {
        return tokenService.verifyTokenRole(token)
    }
    //endregion


    //region deviceTokens
    override suspend fun getDeviceTokens(receiverId: Long): List<String> {
        return deviceTokenDataSource.getDeviceTokens(receiverId)
    }

    override suspend fun saveDeviceTokens(receiverId: Long, token: String) {
        deviceTokenDataSource.saveDeviceTokens(receiverId, token)
    }
//endregion

    //region notification
    override suspend fun sendNotification(
        notification: NotificationRequest
    ): Boolean {
        return notificationDataSource.sendNotification(notification)

    }

    override suspend fun updateNotificationState(receiverId: Long, isRead: Boolean): Boolean {
        return notificationDataSource.updateNotificationState(receiverId,isRead)
    }

    override suspend fun saveNotification(title: String, body: String, receiverId: Long, orderId: Long): Boolean {
        return notificationDataSource.saveNotification(title, body, receiverId, orderId)
    }

    override suspend fun getNotificationHistory(receiverId: Long): List<Notification> {
        return notificationDataSource.getNotificationHistory(receiverId)
    }
    //end region
}