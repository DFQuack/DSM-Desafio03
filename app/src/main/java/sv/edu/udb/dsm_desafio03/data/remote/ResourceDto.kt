package sv.edu.udb.dsm_desafio03.data.remote

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import sv.edu.udb.dsm_desafio03.data.model.Resource
import kotlin.time.Instant

@Serializable
data class ResourceDto(
    val id: String? = null, // Null when creating. MockAPI assigns it
    val title: String,
    val type: String,
    val author: String,
    val code: String? = null,
    val description: String,
    val url: String,
    val imageUrl: String,
    val isFavorite: Boolean = false,
    val rating: Int = 0,
    val createdAt: Long, // MockAPI uses Unix timestamps!!
    val lastModified: Long
)

fun ResourceDto.toDomain() = Resource(
    id = id.orEmpty(), title = title, type = type, author = author, code = code,
    description = description, url = url, imageUrl = imageUrl, isFavorite = isFavorite, rating = rating,
    createdAt = Instant.fromEpochSeconds(createdAt).toLocalDateTime(TimeZone.currentSystemDefault()),
    lastModified = Instant.fromEpochSeconds(lastModified).toLocalDateTime(TimeZone.currentSystemDefault())
)

fun Resource.toDto() = ResourceDto(
    id = id.ifEmpty { null }, title = title, type = type, author = author, code = code,
    description = description, url = url, imageUrl = imageUrl, isFavorite = isFavorite, rating = rating,
    createdAt = createdAt.toInstant(TimeZone.currentSystemDefault()).epochSeconds,
    lastModified = lastModified.toInstant(TimeZone.currentSystemDefault()).epochSeconds
)