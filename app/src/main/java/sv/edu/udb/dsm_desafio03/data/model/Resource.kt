package sv.edu.udb.dsm_desafio03.data.model

import kotlinx.datetime.LocalDate

data class Resource(
    val id: String,
    val title: String,
    val type: String,
    val author: String,
    val code: String?, // ISBN or DOI if applicable
    val description: String,
    val url: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val rating: Int, // Average of all users
    val createdAt: LocalDate,
    val lastModified: LocalDate
)
