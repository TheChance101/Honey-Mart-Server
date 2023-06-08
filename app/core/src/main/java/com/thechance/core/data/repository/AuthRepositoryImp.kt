package com.thechance.core.data.repository

import com.thechance.core.data.datasource.OwnerDataSource
import com.thechance.core.data.datasource.UserDataSource
import com.thechance.core.data.model.Owner
import com.thechance.core.data.model.User
import com.thechance.core.data.security.hashing.HashingService
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.security.token.TokenClaim
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenService

class AuthRepositoryImp(
    private val userDataSource: UserDataSource,
    private val ownerDataSource: OwnerDataSource,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val tokenConfig: TokenConfig
) : AuthRepository {
    //region user
    override suspend fun createUser(userName: String, password: String): Boolean {
        val saltedHash = hashingService.generateSaltedHash(password)
        return userDataSource.createUser(userName, saltedHash)
    }

    override suspend fun isUserNameExists(userName: String): Boolean =
        userDataSource.isUserNameExists(userName)

    override suspend fun getUserByName(userName:String):User{
        return userDataSource.getUserByName(userName)
    }

    override fun getToken(user: User): String {
        return tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.userId.toString()
            )
        )
    }

    override fun isValidPassword(user: User, password: String) = hashingService.verify(
        value = password,
        saltedHash = SaltedHash(
            hash = user.password,
            salt = user.salt
        )
    )


    //endregion

    //region owner
    override suspend fun createOwner(userName: String, password: String): Owner =
        ownerDataSource.createOwner(userName, password)

    override suspend fun isOwnerNameExists(ownerName: String): Boolean =
        ownerDataSource.isOwnerNameExists(ownerName)

    //endregion

}