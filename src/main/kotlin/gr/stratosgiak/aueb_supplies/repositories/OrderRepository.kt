package gr.stratosgiak.aueb_supplies.repositories

import gr.stratosgiak.aueb_supplies.models.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Int>