package gr.stratosgiak.aueb_supplies.models.dto

import java.time.OffsetDateTime

data class StaffDto(
    val id: Int?,
    val email: String,
    val username: String,
    val password: String,
    val dateCreated: OffsetDateTime?
)