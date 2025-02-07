package gr.stratosgiak.aueb_supplies.services

import gr.stratosgiak.aueb_supplies.models.OrderPartial
import gr.stratosgiak.aueb_supplies.models.entities.OrderEntity

interface OrderService {
    fun create(order: OrderEntity): OrderEntity
    fun update(id: Int, order: OrderEntity): OrderEntity
    fun partialUpdate(id: Int, orderUpdate: OrderPartial): OrderEntity
    fun list(): List<OrderEntity>
    fun get(id: Int): OrderEntity?
    fun delete(id: Int)
}