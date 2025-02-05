package gr.stratosgiak.aueb_supplies.controllers

import gr.stratosgiak.aueb_supplies.models.dto.StaffDto
import gr.stratosgiak.aueb_supplies.services.StaffService
import gr.stratosgiak.aueb_supplies.toStaffDto
import gr.stratosgiak.aueb_supplies.toStaffEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/v1/staff"])
class StaffControllerV1(private val staffService: StaffService) {
    @PostMapping
    fun createStaff(@RequestBody staffDto: StaffDto): ResponseEntity<StaffDto> {
        try {
            val createdStaff = staffService.create(staffDto.toStaffEntity()).toStaffDto()
            return ResponseEntity(createdStaff, HttpStatus.CREATED)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping(path = ["/{id}"])
    fun updateStaff(@PathVariable("id") id: Int, @RequestBody staffDto: StaffDto): ResponseEntity<StaffDto> {
        try {
            val updatedStaff = staffService.update(id, staffDto.toStaffEntity())
            return ResponseEntity(updatedStaff.toStaffDto(), HttpStatus.OK)
        } catch (e: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun readManyStaff(): List<StaffDto> {
        return staffService.list().map { it.toStaffDto() }
    }

    @GetMapping(path = ["/{id}"])
    fun readOneStaff(@PathVariable("id") id: Int): ResponseEntity<StaffDto> {
        val staff = staffService.get(id)?.toStaffDto()
        return staff?.let { ResponseEntity.ok(it) } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }
}