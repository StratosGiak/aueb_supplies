package gr.stratosgiak.aueb_supplies.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ninjasquad.springmockk.MockkBean
import gr.stratosgiak.aueb_supplies.models.entities.StaffEntity
import gr.stratosgiak.aueb_supplies.services.StaffService
import gr.stratosgiak.aueb_supplies.testStaffDto
import gr.stratosgiak.aueb_supplies.testStaffEntity
import gr.stratosgiak.aueb_supplies.testStaffPartialDto
import gr.stratosgiak.aueb_supplies.toStaffEntity
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

private const val STAFF_URL = "/v1/staff"

@SpringBootTest
@AutoConfigureMockMvc
class StaffControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean val staffService: StaffService
) {
    val objectMapper: ObjectMapper = JsonMapper.builder()
        .addModule(JavaTimeModule())
        .build()

    @BeforeEach
    fun beforeEach() {
        every {
            staffService.create(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `test create staff saves staff`() {
        val testStaffDto = testStaffDto()
        mockMvc.post(STAFF_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffDto
            )
        }
        verify { staffService.create(testStaffDto.toStaffEntity()) }
    }

    @Test
    fun `test create staff returns 201 on success`() {
        mockMvc.post(STAFF_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffDto()
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test create staff returns 400 when IllegalArgumentException thrown`() {
        every {
            staffService.create(any())
        } throws (IllegalArgumentException())

        mockMvc.post(STAFF_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffDto()
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test update staff returns 400 when IllegalStateException thrown`() {
        every {
            staffService.update(any(), any())
        } throws (IllegalStateException())

        val id = 99
        val testStaffDto = testStaffDto()
        mockMvc.put("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffDto
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test update staff returns 200 and updated staff`() {
        every {
            staffService.update(any(), any())
        } answers {
            secondArg<StaffEntity>().copy(id = firstArg())
        }

        val id = 99
        val testStaffDto = testStaffDto()
        mockMvc.put("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffDto
            )
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(id)) }
            content { jsonPath("$.username", equalTo(testStaffDto.username)) }
            content { jsonPath("$.password", equalTo(testStaffDto.password)) }
            content { jsonPath("$.email", equalTo(testStaffDto.email)) }
        }
    }

    @Test
    fun `test partial update staff returns 400 when IllegalStateException thrown`() {
        every {
            staffService.partialUpdate(any(), any())
        } throws (IllegalStateException())

        val id = 99
        val testStaffPartialDto = testStaffPartialDto()
        mockMvc.patch("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffPartialDto
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test partial update staff returns 200 and updated staff`() {
        val id = 99
        val testStaffEntity = testStaffEntity(id = id)
        every {
            staffService.partialUpdate(any(), any())
        } answers {
            testStaffEntity
        }

        val testStaffPartialDto = testStaffPartialDto()
        mockMvc.patch("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testStaffPartialDto
            )
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(testStaffEntity.id)) }
            content { jsonPath("$.username", equalTo(testStaffPartialDto.username ?: testStaffEntity.username)) }
            content { jsonPath("$.password", equalTo(testStaffPartialDto.password ?: testStaffEntity.password)) }
            content { jsonPath("$.email", equalTo(testStaffPartialDto.email ?: testStaffEntity.email)) }
            content { jsonPath("$.dateCreated", equalTo(testStaffEntity.dateCreated)) }
        }
    }

    @Test
    fun `test get list returns empty list and 200 status when no staff in database`() {
        every {
            staffService.list()
        } answers {
            emptyList()
        }

        mockMvc.get(STAFF_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `test get list returns staff list and 200 status when staff is in database`() {
        val testStaffEntity = testStaffEntity(99)
        every {
            staffService.list()
        } answers {
            listOf(testStaffEntity)
        }

        mockMvc.get(STAFF_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", equalTo(testStaffEntity.id)) }
            content { jsonPath("$[0].username", equalTo(testStaffEntity.username)) }
            content { jsonPath("$[0].password", equalTo(testStaffEntity.password)) }
            content { jsonPath("$[0].email", equalTo(testStaffEntity.email)) }
            content { jsonPath("$[0].dateCreated", equalTo(testStaffEntity.dateCreated)) }
        }
    }

    @Test
    fun `test get staff returns 404 when not found`() {
        every {
            staffService.get(any())
        } answers {
            null
        }
        val id = 99
        mockMvc.get("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `test get staff returns 200 when found`() {
        val id = 99
        val testStaffEntity = testStaffEntity(id = id)
        every {
            staffService.get(any())
        } answers {
            testStaffEntity
        }

        mockMvc.get("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(testStaffEntity.id)) }
            content { jsonPath("$.username", equalTo(testStaffEntity.username)) }
            content { jsonPath("$.password", equalTo(testStaffEntity.password)) }
            content { jsonPath("$.email", equalTo(testStaffEntity.email)) }
            content { jsonPath("$.dateCreated", equalTo(testStaffEntity.dateCreated)) }
        }
    }

    @Test
    fun `test delete staff returns 204 on success`() {
        every {
            staffService.delete(any())
        } answers { }

        val id = 99
        mockMvc.delete("$STAFF_URL/$id") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
        }
    }
}