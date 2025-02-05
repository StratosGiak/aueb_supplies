package gr.stratosgiak.aueb_supplies.models

import java.time.OffsetDateTime

data class StaffPartial(
    val id: Int? = null,
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val dateCreated: OffsetDateTime? = null,
)