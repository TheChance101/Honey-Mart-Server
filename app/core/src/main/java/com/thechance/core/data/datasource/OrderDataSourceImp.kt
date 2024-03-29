package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.data.datasource.database.tables.order.OrderProductTable
import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.data.datasource.database.tables.product.GalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductGalleryTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.mapper.toMarketOrder
import com.thechance.core.data.datasource.mapper.toProductInOrder
import com.thechance.core.data.datasource.mapper.toUserOrder
import com.thechance.core.data.repository.dataSource.OrderDataSource
import com.thechance.core.entity.Image
import com.thechance.core.entity.order.*
import com.thechance.core.utils.ORDER_STATUS_DELETED
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.*

class OrderDataSourceImp : OrderDataSource {

    override suspend fun createOrder(
        userId: Long, marketId: Long, products: List<OrderItem>, totalPrice: Double
    ): Boolean = dbQuery {
        val newOrder = OrderTable.insert {
            it[this.userId] = userId
            it[this.marketId] = marketId
            it[this.totalPrice] = totalPrice
        }
        OrderProductTable.batchInsert(products) { orderItem ->
            this[OrderProductTable.orderId] = newOrder[OrderTable.id]
            this[OrderProductTable.productId] = orderItem.productId
            this[OrderProductTable.count] = orderItem.count
        }
        true
    }

    override suspend fun getOrdersForMarket(marketId: Long, state: Int): List<MarketOrder> = dbQuery {
        (OrderTable innerJoin NormalUserTable).select {
            (OrderTable.marketId eq marketId) and
                    not(OrderTable.state eq ORDER_STATUS_DELETED) and
                    (OrderTable.state eq state)
        }.map { it.toMarketOrder() }
    }

    override suspend fun getAllOrdersForMarket(marketId: Long): List<MarketOrder> = dbQuery {
        (OrderTable innerJoin NormalUserTable).select {
            (OrderTable.marketId eq marketId) and
                    not(OrderTable.state eq ORDER_STATUS_DELETED)
        }.map { it.toMarketOrder() }
    }

    override suspend fun getOrdersForUser(userId: Long, state: Int): List<UserOrder> = dbQuery {
        (OrderTable innerJoin MarketTable)
            .select {
                (OrderTable.userId eq userId) and
                        not(OrderTable.state eq ORDER_STATUS_DELETED) and
                        (OrderTable.state eq state)
            }.map {
                val itemsCount = OrderProductTable.select { OrderProductTable.orderId eq it[OrderTable.id] }.count()
                it.toUserOrder(itemsCount)
            }
    }

    override suspend fun getAllOrdersForUser(userId: Long): List<UserOrder> = dbQuery {
        (OrderTable innerJoin MarketTable).select {
            (OrderTable.userId eq userId) and
                    not(OrderTable.state eq ORDER_STATUS_DELETED)
        }.map {
            val itemsCount = OrderProductTable.select { OrderProductTable.orderId eq it[OrderTable.id] }.count()
            it.toUserOrder(itemsCount)
        }
    }

    override suspend fun getOrderById(orderId: Long): OrderDetails = dbQuery {
        val order = OrderTable.select { OrderTable.id eq orderId }.single()
        val products = OrderProductTable.innerJoin(ProductTable)
            .select {
                (OrderProductTable.orderId eq orderId) and
                        (OrderProductTable.productId eq ProductTable.id)
            }.map { productRow ->
                val images = getProductImages(productRow[ProductTable.id].value)
                productRow.toProductInOrder(images)
            }


        OrderDetails(
            orderId = orderId,
            userId = order[OrderTable.userId].value,
            marketId = order[OrderTable.marketId].value,
            products = products,
            totalPrice = order[OrderTable.totalPrice],
            date = order[OrderTable.orderDate],
            state = order[OrderTable.state]
        )
    }

    private fun getProductImages(productId: Long): List<Image> {
        return (GalleryTable innerJoin ProductGalleryTable)
            .select { ProductGalleryTable.productId eq productId }
            .map { imageRow ->
                Image(
                    id = imageRow[GalleryTable.id].value,
                    imageUrl = imageRow[GalleryTable.imageUrl],
                )
            }
    }

    override suspend fun updateOrderState(orderId: Long, newState: Int): Boolean = dbQuery {
        OrderTable.update({ OrderTable.id eq orderId }) { it[state] = newState }
        true
    }

    override suspend fun isOrderExist(orderId: Long): Boolean =
        dbQuery {
            OrderTable.select { OrderTable.id eq orderId }.singleOrNull() != null
        }

    override suspend fun getOrderState(orderId: Long): Int = dbQuery {
        OrderTable.select {
            OrderTable.id eq orderId
        }.map {
            it[OrderTable.state]
        }.single()
    }

    override suspend fun getUserLatestOrderId(userId: Long): Long? {
        return dbQuery {
            OrderTable.select {
                OrderTable.userId eq userId
            }.orderBy(OrderTable.orderDate, SortOrder.DESC)
                .limit(1)
                .map { it[OrderTable.id] }
                .singleOrNull()?.value
        }
    }

}