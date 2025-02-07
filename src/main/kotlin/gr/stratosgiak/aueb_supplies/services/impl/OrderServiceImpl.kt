package gr.stratosgiak.aueb_supplies.services.impl

import gr.stratosgiak.aueb_supplies.models.OrderPartial
import gr.stratosgiak.aueb_supplies.models.entities.OrderEntity
import gr.stratosgiak.aueb_supplies.repositories.OrderRepository
import gr.stratosgiak.aueb_supplies.services.OrderService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {
    override fun create(order: OrderEntity): OrderEntity {
        require(order.id == null)
        return orderRepository.save(order)
    }

    @Transactional
    override fun update(id: Int, order: OrderEntity): OrderEntity {
        val existing = orderRepository.findByIdOrNull(id)
        check(existing != null)
        val normalizedOrder = order.copy(
            id = id,
            dateCreated = existing.dateCreated,
        )
        return orderRepository.save(normalizedOrder)
    }

    @Transactional
    override fun partialUpdate(id: Int, orderUpdate: OrderPartial): OrderEntity {
        val existing = orderRepository.findByIdOrNull(id)
        checkNotNull(existing)
        val updated = existing.copy(
            name = orderUpdate.name ?: existing.name,
            email = orderUpdate.email ?: existing.email,
            comments = orderUpdate.comments ?: existing.comments,
            statusEntity = orderUpdate.status ?: existing.statusEntity,
        )
        return orderRepository.save(updated)
    }

    override fun list(): List<OrderEntity> {
        return orderRepository.findAll()
    }

    override fun get(id: Int): OrderEntity? {
        return orderRepository.findByIdOrNull(id)
    }

    override fun delete(id: Int) {
        orderRepository.deleteById(id)
    }
}