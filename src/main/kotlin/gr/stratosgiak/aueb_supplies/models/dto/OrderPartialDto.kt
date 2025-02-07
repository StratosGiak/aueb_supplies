package gr.stratosgiak.aueb_supplies.models.dto

import java.time.OffsetDateTime

data class OrderPartialDto(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val comments: String? = null,
    val dateCreated: OffsetDateTime? = null,
    val status: Int? = null,
)