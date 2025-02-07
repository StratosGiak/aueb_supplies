package gr.stratosgiak.aueb_supplies.models.entities

import jakarta.persistence.*
import org.hibernate.annotations.CurrentTimestamp
import java.time.OffsetDateTime

@Entity(name = "orders")
@Table(name = "orders")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String,
    val email: String,
    val comments: String,

    @Column(name = "date_created", updatable = false)
    @CurrentTimestamp
    val dateCreated: OffsetDateTime?,

    @JoinColumn(name = "status_id")
    @ManyToOne
    val statusEntity: OrderStatusEntity,
)