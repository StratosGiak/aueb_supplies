package gr.stratosgiak.aueb_supplies.models.entities

import jakarta.persistence.*
import org.hibernate.annotations.CurrentTimestamp
import java.time.OffsetDateTime

@Entity
@Table(name = "staff")
data class StaffEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val email: String,
    val username: String,
    val password: String,
    @Column(name = "date_created", updatable = false)
    @CurrentTimestamp
    val dateCreated: OffsetDateTime?
)
