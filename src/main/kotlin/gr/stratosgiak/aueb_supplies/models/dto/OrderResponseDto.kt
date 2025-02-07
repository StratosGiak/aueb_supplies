package gr.stratosgiak.aueb_supplies.models.dto

import java.time.OffsetDateTime

data class OrderResponseDto(
    val id: Int,
    val name: String,
    val email: String,
    val comments: String,
    val dateCreated: OffsetDateTime,
    val status: OrderStatusDto,
)