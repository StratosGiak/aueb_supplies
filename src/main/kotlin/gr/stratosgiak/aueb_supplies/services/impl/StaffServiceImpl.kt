package gr.stratosgiak.aueb_supplies.services.impl

import gr.stratosgiak.aueb_supplies.models.StaffPartial
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity
import gr.stratosgiak.aueb_supplies.repositories.StaffRepository
import gr.stratosgiak.aueb_supplies.services.StaffService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StaffServiceImpl(private val staffRepository: StaffRepository) : StaffService {
    override fun create(staff: StaffEntity): StaffEntity {
        require(staff.id == null)
        return staffRepository.save(staff)
    }

    @Transactional
    override fun update(id: Int, staff: StaffEntity): StaffEntity {
        val existing = staffRepository.findByIdOrNull(id)
        check(existing != null)
        val normalizedStaff = staff.copy(id = id, dateCreated = existing.dateCreated)
        return staffRepository.save(normalizedStaff)
    }

    override fun list(): List<StaffEntity> {
        return staffRepository.findAll()
    }

    override fun get(id: Int): StaffEntity? {
        return staffRepository.findByIdOrNull(id)
    }
}