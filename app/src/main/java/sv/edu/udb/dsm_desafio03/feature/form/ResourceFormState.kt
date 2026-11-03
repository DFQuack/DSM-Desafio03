package sv.edu.udb.dsm_desafio03.feature.form

data class ResourceFormState(
    val title: String = "",
    val type: String = "",
    val author: String = "",
    val code: String = "",
    val description: String = "",
    val url: String = "",
    val imageUrl: String = "",
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val userMessage: String? = null
)
