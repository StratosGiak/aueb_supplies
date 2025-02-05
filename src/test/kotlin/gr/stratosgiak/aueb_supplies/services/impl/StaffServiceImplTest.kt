package gr.stratosgiak.aueb_supplies.services.impl

import gr.stratosgiak.aueb_supplies.models.StaffPartial
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity
import gr.stratosgiak.aueb_supplies.repositories.StaffRepository
import gr.stratosgiak.aueb_supplies.testStaffEntity
import gr.stratosgiak.aueb_supplies.testStaffEntityAlt
import gr.stratosgiak.aueb_supplies.testStaffPartial
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
@Transactional
class StaffServiceImplTest @Autowired constructor(
    private val staffService: StaffServiceImpl,
    private val staffRepository: StaffRepository
) {
    @Test
    fun `test save persists staff to database`() {
        val saved = staffService.create(testStaffEntity())
        assertThat(saved.id).isNotNull()
        val loaded = staffRepository.findByIdOrNull(saved.id!!)
        assertThat(loaded).isNotNull()
        assertThat(loaded).isEqualTo(testStaffEntity(id = saved.id, dateCreated = saved.dateCreated))
    }

    @Test
    fun `test create staff with existing id throws IllegalArgumentException`() {
        assertThrows<IllegalArgumentException> {
            val existing = testStaffEntity(id = 99)
            staffService.create(existing)
        }
    }

    @Test
    fun `test update updates staff in database`() {
        val existing = staffRepository.save(testStaffEntity())
        assertThat(existing.id).isNotNull()
        val id = existing.id!!
        val updated = testStaffEntityAlt()
        val result = staffService.update(id, updated)
        assertThat(result).isEqualTo(updated.copy(id = id, dateCreated = existing.dateCreated))
        val retrieved = staffRepository.findByIdOrNull(id)
        assertThat(retrieved).isNotNull()
        assertThat(retrieved).isEqualTo(updated.copy(id = id, dateCreated = existing.dateCreated))
    }

    @Test
    fun `test update throws IllegalStateException when staff not in database`() {
        assertThrows<IllegalStateException> {
            val id = 99
            val updated = testStaffEntityAlt()
            staffService.update(id, updated)
        }
    }

    @Test
    fun `test partial update throws IllegalStateException when staff not in database`() {
        assertThrows<IllegalStateException> {
            val id = 99
            val updated = testStaffPartial()
            staffService.partialUpdate(id, updated)
        }
    }

    @Test
    fun `test partial update does not update when all null`() {
        val existing = staffRepository.save(testStaffEntity())
        assertThat(existing.id).isNotNull()
        val updated = staffService.partialUpdate(existing.id!!, StaffPartial())
        assertThat(updated).isEqualTo(existing)
    }

    @Test
    fun `test partial update updates username`() {
        val newUsername = "newusername"
        val existingStaff = testStaffEntity()
        val staffUpdate = StaffPartial(username = newUsername)
        val expectedStaff = existingStaff.copy(username = newUsername)
        assertStaffPartialUpdateIsUpdated(
            existingStaff = existingStaff,
            staffUpdate = staffUpdate,
            expectedStaff = expectedStaff,
        )
    }

    @Test
    fun `test get list returns empty when no staff in database`() {
        val result = staffService.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `test get list returns staff when staff in database`() {
        val saved = staffRepository.save(testStaffEntity())
        val expected = listOf(saved)
        val loaded = staffService.list()
        assertThat(loaded).isEqualTo(expected)
    }

    @Test
    fun `test get returns null when staff not in database`() {
        val result = staffService.get(99)
        assertThat(result).isNull()
    }

    @Test
    fun `test get returns staff when staff in database`() {
        val saved = staffRepository.save(testStaffEntity())
        assertThat(saved.id).isNotNull()
        val loaded = staffService.get(saved.id!!)
        assertThat(loaded).isEqualTo(saved)
    }

    @Test
    fun `test delete non existing staff does nothing`() {
        val id = 99
        staffService.delete(id)
        assertThat(staffRepository.existsById(id)).isFalse()
    }

    @Test
    fun `test delete existing staff deletes staff from database`() {
        val existing = staffRepository.save(testStaffEntity())
        assertThat(existing.id).isNotNull()
        val id = existing.id!!
        staffService.delete(id)
        assertThat(staffRepository.existsById(id)).isFalse()
    }

    private fun assertStaffPartialUpdateIsUpdated(
        existingStaff: StaffEntity,
        staffUpdate: StaffPartial,
        expectedStaff: StaffEntity,
    ) {
        val savedExisting = staffRepository.save(existingStaff)
        assertThat(savedExisting.id).isNotNull()
        val existingId = savedExisting.id!!
        val updated = staffService.partialUpdate(
            existingId, staffUpdate
        )
        val expected = expectedStaff.copy(id = existingId, dateCreated = savedExisting.dateCreated)
        assertThat(updated).isEqualTo(expected)
        val retrieved = staffRepository.findByIdOrNull(existingId)
        assertThat(retrieved).isNotNull()
        assertThat(retrieved).isEqualTo(expected)
    }
}