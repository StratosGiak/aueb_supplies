package gr.stratosgiak.aueb_supplies

import gr.stratosgiak.aueb_supplies.models.StaffPartial
import gr.stratosgiak.aueb_supplies.models.dto.StaffDto
import gr.stratosgiak.aueb_supplies.models.dto.StaffPartialDto
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity

fun StaffEntity.toStaffDto() = StaffDto(
    id = id,
    email = email,
    username = username,
    password = password,
    dateCreated = dateCreated,
)

fun StaffDto.toStaffEntity() = StaffEntity(
    id = id,
    email = email,
    username = username,
    password = password,
    dateCreated = dateCreated,
)

fun StaffPartialDto.toStaffPartial() = StaffPartial(
    id = id,
    email = email,
    username = username,
    password = password,
    dateCreated = dateCreated,
)