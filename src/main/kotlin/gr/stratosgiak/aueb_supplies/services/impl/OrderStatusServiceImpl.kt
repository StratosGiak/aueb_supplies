package gr.stratosgiak.aueb_supplies.services.impl

import gr.stratosgiak.aueb_supplies.models.entities.OrderStatusEntity
import gr.stratosgiak.aueb_supplies.repositories.OrderStatusRepository
import gr.stratosgiak.aueb_supplies.services.OrderStatusService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderStatusServiceImpl(private val orderStatusRepository: OrderStatusRepository) : OrderStatusService {
    override fun list(): List<OrderStatusEntity> {
        return orderStatusRepository.findAll()
    }

    override fun get(id: Int): OrderStatusEntity? {
        return orderStatusRepository.findByIdOrNull(id)
    }
}