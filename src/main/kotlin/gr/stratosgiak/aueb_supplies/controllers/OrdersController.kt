package gr.stratosgiak.aueb_supplies.controllers

import gr.stratosgiak.aueb_supplies.models.dto.OrderPartialDto
import gr.stratosgiak.aueb_supplies.models.dto.OrderRequestDto
import gr.stratosgiak.aueb_supplies.models.dto.OrderResponseDto
import gr.stratosgiak.aueb_supplies.services.OrderService
import gr.stratosgiak.aueb_supplies.services.OrderStatusService
import gr.stratosgiak.aueb_supplies.toOrderEntity
import gr.stratosgiak.aueb_supplies.toOrderPartial
import gr.stratosgiak.aueb_supplies.toOrderResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/v1/orders"])
class OrdersControllerV1(
    private val orderService: OrderService,
    private val orderStatusService: OrderStatusService,
) {
    @PostMapping
    fun createOrder(@RequestBody orderDto: OrderRequestDto): ResponseEntity<OrderResponseDto> {
        try {
            val statusEntity =
                orderStatusService.get(orderDto.status) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
            val createdOrder = orderService.create(orderDto.toOrderEntity(statusEntity)).toOrderResponseDto()
            return ResponseEntity(createdOrder, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }


    @PutMapping(path = ["/{id}"])
    fun updateOrder(
        @PathVariable("id") id: Int,
        @RequestBody orderDto: OrderRequestDto
    ): ResponseEntity<OrderResponseDto> {
        try {
            val statusEntity =
                orderStatusService.get(orderDto.status) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
            val updatedOrder = orderService.update(id, orderDto.toOrderEntity(statusEntity))
            return ResponseEntity(updatedOrder.toOrderResponseDto(), HttpStatus.OK)
        } catch (e: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path = ["/{id}"])
    fun partialUpdateOrder(
        @PathVariable("id") id: Int,
        @RequestBody orderPartialDto: OrderPartialDto
    ): ResponseEntity<OrderResponseDto> {
        try {
            val statusEntity =
                if (orderPartialDto.status != null)
                    orderStatusService.get(orderPartialDto.status) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
                else null
            val updatedOrder = orderService.partialUpdate(id, orderPartialDto.toOrderPartial(statusEntity))
            return ResponseEntity(updatedOrder.toOrderResponseDto(), HttpStatus.OK)
        } catch (e: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun readManyOrders(): List<OrderResponseDto> {
        return orderService.list().map { it.toOrderResponseDto() }
    }

    @GetMapping(path = ["/{id}"])
    fun readOneOrder(@PathVariable("id") id: Int): ResponseEntity<OrderResponseDto> {
        val order = orderService.get(id)?.toOrderResponseDto()
        return order?.let { ResponseEntity.ok(it) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteOrder(@PathVariable("id") id: Int): ResponseEntity<Unit> {
        orderService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}