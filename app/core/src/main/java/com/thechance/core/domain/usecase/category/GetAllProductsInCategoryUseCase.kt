package com.thechance.core.domain.usecase.category

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.product.Product
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class GetAllProductsInCategoryUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(categoryId: Long?, page:Int?): List<Product> {
        return if (isInvalidId(categoryId)) {
            throw InvalidCategoryIdException()
        }else if(isInvalidPageNumber(page)){
            throw InvalidPageNumberException()
        } else {
            val isCategoryDeleted = repository.isCategoryDeleted(categoryId!!)
            if (isCategoryDeleted == null) {
                throw IdNotFoundException()
            } else if (!isCategoryDeleted) {
                repository.getAllProductsInCategory(categoryId,page!!)
            } else {
                throw CategoryDeletedException()
            }
        }
    }
}