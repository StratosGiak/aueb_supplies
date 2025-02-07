package gr.stratosgiak.aueb_supplies

import gr.stratosgiak.aueb_supplies.models.OrderPartial
import gr.stratosgiak.aueb_supplies.models.StaffPartial
import gr.stratosgiak.aueb_supplies.models.dto.*
import gr.stratosgiak.aueb_supplies.models.entities.OrderEntity
import gr.stratosgiak.aueb_supplies.models.entities.OrderStatusEntity
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity

fun OrderStatusEntity.toOrderStatusResponseDto(): OrderStatusDto {
    check(id != null)
    return OrderStatusDto(
        id = id!!,
        name = name,
    )
}

fun OrderStatusDto.toOrderStatusEntity() = OrderStatusEntity(
    id = id,
    name = name,
)

fun OrderEntity.toOrderResponseDto(): OrderResponseDto {
    check(id != null)
    check(dateCreated != null)
    return OrderResponseDto(
        id = id!!,
        name = name,
        email = email,
        comments = comments,
        dateCreated = dateCreated!!,
        status = statusEntity.toOrderStatusResponseDto(),
    )
}

fun OrderRequestDto.toOrderEntity(statusEntity: OrderStatusEntity) = OrderEntity(
    id = null,
    name = name,
    email = email,
    comments = comments,
    dateCreated = null,
    statusEntity = statusEntity,
)

fun OrderPartialDto.toOrderPartial(statusEntity: OrderStatusEntity?) = OrderPartial(
    id = id,
    name = name,
    email = email,
    comments = comments,
    status = statusEntity,
    dateCreated = dateCreated,
)

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