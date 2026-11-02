package sv.edu.udb.dsm_desafio03.data.model

import kotlinx.datetime.LocalDateTime

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
    val rating: Int,
    val createdAt: LocalDateTime,
    val lastModified: LocalDateTime
)
