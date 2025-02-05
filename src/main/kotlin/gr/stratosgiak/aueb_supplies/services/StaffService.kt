package gr.stratosgiak.aueb_supplies.services

import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity

interface StaffService {
    fun create(staff: StaffEntity): StaffEntity
    fun update(id: Int, staff: StaffEntity): StaffEntity
    fun list(): List<StaffEntity>
    fun get(id: Int): StaffEntity?
}