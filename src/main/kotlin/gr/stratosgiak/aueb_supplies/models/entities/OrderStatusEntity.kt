package gr.stratosgiak.aueb_supplies.models.entities

import jakarta.persistence.*

@Entity
@Table(name = "order_statuses")
data class OrderStatusEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,
    val name: String,

    @OneToMany(mappedBy = "statusEntity")
    val orderEntities: List<OrderEntity> = emptyList()
)