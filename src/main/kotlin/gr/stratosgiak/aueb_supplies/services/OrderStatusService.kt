package gr.stratosgiak.aueb_supplies.services

import gr.stratosgiak.aueb_supplies.models.entities.OrderStatusEntity

interface OrderStatusService {
    fun list(): List<OrderStatusEntity>
    fun get(id: Int): OrderStatusEntity?
}