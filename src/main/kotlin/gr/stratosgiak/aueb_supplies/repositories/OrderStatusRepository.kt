package gr.stratosgiak.aueb_supplies.repositories

import gr.stratosgiak.aueb_supplies.models.entities.OrderStatusEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderStatusRepository : JpaRepository<OrderStatusEntity, Int>