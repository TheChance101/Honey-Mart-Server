package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import org.koin.core.component.KoinComponent

class SendNotificationByTokenUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userTokens: List<String>,title:String,body:String):List<String> {
        return repository.sendNotificationByToken(userTokens,title,body)
    }
}