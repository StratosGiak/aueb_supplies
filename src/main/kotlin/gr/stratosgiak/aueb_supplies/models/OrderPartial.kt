package gr.stratosgiak.aueb_supplies.models

import gr.stratosgiak.aueb_supplies.models.entities.OrderStatusEntity
import java.time.OffsetDateTime

data class OrderPartial(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val comments: String? = null,
    val dateCreated: OffsetDateTime? = null,
    val status: OrderStatusEntity? = null,
)