package gr.stratosgiak.aueb_supplies.models.dto

import java.time.OffsetDateTime

data class StaffPartialDto(
    val id: Int? = null,
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val dateCreated: OffsetDateTime? = null,
)