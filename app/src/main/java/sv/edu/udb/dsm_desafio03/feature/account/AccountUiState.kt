package sv.edu.udb.dsm_desafio03.feature.account

import sv.edu.udb.dsm_desafio03.data.model.Resource

data class AccountUiState(
    val favorites: List<Resource> = emptyList(),
    val userMessage: String? = null
)
