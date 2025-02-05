package gr.stratosgiak.aueb_supplies

import gr.stratosgiak.aueb_supplies.models.StaffPartial
import gr.stratosgiak.aueb_supplies.models.dto.StaffDto
import gr.stratosgiak.aueb_supplies.models.dto.StaffPartialDto
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity
import java.time.OffsetDateTime

fun testStaffDto(id: Int? = null, dateCreated: OffsetDateTime? = null) = StaffDto(
    id = id,
    username = "johnsmith",
    email = "email@email.com",
    password = "password",
    dateCreated = dateCreated,
)

fun testStaffPartial(id: Int? = null, dateCreated: OffsetDateTime? = null) = StaffPartial(
    id = id,
    username = "johnsmith",
    email = "email@email.com",
    dateCreated = dateCreated,
)

fun testStaffPartialDto(id: Int? = null, dateCreated: OffsetDateTime? = null) = StaffPartialDto(
    id = id,
    username = "johnsmith",
    email = "email@email.com",
    dateCreated = dateCreated,
)

fun testStaffEntity(id: Int? = null, dateCreated: OffsetDateTime? = null) = StaffEntity(
    id = id,
    username = "johnsmith",
    email = "email@email.com",
    password = "password",
    dateCreated = dateCreated,
)

fun testStaffEntityAlt(id: Int? = null, dateCreated: OffsetDateTime? = null) = StaffEntity(
    id = id,
    username = "seanjith",
    email = "alt@email.com",
    password = "altword",
    dateCreated = dateCreated,
)