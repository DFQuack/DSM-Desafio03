package sv.edu.udb.dsm_desafio03.feature.resources

import sv.edu.udb.dsm_desafio03.data.model.Resource

data class ResourceUiState(
    val resources: List<Resource> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val userMessage: String? = null
)
