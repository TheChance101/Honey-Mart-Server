package com.thechance.core.domain.usecase.order

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Cart
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateOrderUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, role: String?): Boolean {
        return if (isInvalidId(userId) || !isValidRole(NORMAL_USER_ROLE, role)) {
            throw InvalidUserIdException()
        } else if (!isEmptyCart(getCartId(userId!!))) {
            val cartId = getCartId(userId)
            val cart = repository.getCart(cartId)
            val totalPrice = calculateTotalPrice(cart, userId)
            val isCreated = repository.createOrder(userId, cart, totalPrice)
            if (isCreated) {
                repository.deleteAllProductsInCart(getCartId(userId))
            }
            isCreated
        } else {
            throw EmptyCartException()
        }
    }

    private suspend fun calculateTotalPrice(cart: Cart, userId: Long): Double {
        val coupons = repository.getClippedCouponsForUser(userId)
        val newTotalPrice = cart.products.sumOf { product ->
            val coupon = coupons.find { it.product.id == product.id }
            if (coupon != null) {
                repository.useCoupon(coupon.couponId, userId)
            }
            val discountPercentage = coupon?.discountPercentage ?: 0.0
            val discountAmount = product.price * (discountPercentage / 100)
            val adjustedPrice = product.price - discountAmount
            if (product.count > 1) {
                adjustedPrice + (product.price * product.count - 1)
            } else {
                adjustedPrice * product.count
            }
        }
        return newTotalPrice
    }

    private suspend fun getCartId(userId: Long): Long {
        return repository.getCartId(userId) ?: repository.createCart(userId)
    }

    private suspend fun isEmptyCart(cartId: Long): Boolean {
        return repository.getCart(cartId).products.isEmpty()
    }
}