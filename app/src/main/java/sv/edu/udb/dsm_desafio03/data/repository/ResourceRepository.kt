package sv.edu.udb.dsm_desafio03.data.repository

import kotlinx.coroutines.flow.StateFlow
import sv.edu.udb.dsm_desafio03.data.model.Resource

interface ResourceRepository {
    val resources: StateFlow<List<Resource>>
    suspend fun refresh()
    fun getResource(id: String): Resource?
    suspend fun create(resource: Resource)
    suspend fun update(resource: Resource)
    suspend fun delete(id: String)
}