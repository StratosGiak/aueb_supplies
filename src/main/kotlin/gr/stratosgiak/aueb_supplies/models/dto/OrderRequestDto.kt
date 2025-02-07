package gr.stratosgiak.aueb_supplies.models.dto

data class OrderRequestDto(
    val name: String,
    val email: String,
    val comments: String,
    val status: Int,
)