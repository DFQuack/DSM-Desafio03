package sv.edu.udb.dsm_desafio03.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sv.edu.udb.dsm_desafio03.data.model.Resource
import sv.edu.udb.dsm_desafio03.data.remote.ResourceApi
import sv.edu.udb.dsm_desafio03.data.remote.toDomain
import sv.edu.udb.dsm_desafio03.data.remote.toDto
import javax.inject.Inject
import javax.inject.Singleton

/*
* Middle layer between network (DTO, Retrofit) and ViewModel (UI state) to separate concerns.
*/

@Singleton // Creates only one instance for the whole app and reuses it
class DefaultResourceRepository @Inject constructor(
    private val api: ResourceApi
) : ResourceRepository {
    // Single source of truth that holds the current list
    private val _resources = MutableStateFlow<List<Resource>>(emptyList())
    // Read-only version
    override val resources: StateFlow<List<Resource>> = _resources.asStateFlow()

    // API methods
    override suspend fun refresh() {
        _resources.value = api.getAll().map { it.toDomain() }
    }

    override fun getResource(id: String) = _resources.value.firstOrNull { it.id == id }

    override suspend fun create(resource: Resource) {
        val created = api.create(resource.toDto()).toDomain()
        _resources.update { it + created }
    }

    override suspend fun update(resource: Resource) {
        val updated = api.update(resource.id, resource.toDto()).toDomain()
        _resources.update { list -> list.map { if (it.id == updated.id) updated else it } }
    }

    override suspend fun delete(id: String) {
        api.delete(id)
        _resources.update { list -> list.filterNot { it.id == id } }
    }
}