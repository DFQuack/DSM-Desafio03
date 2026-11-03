package sv.edu.udb.dsm_desafio03.feature.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.dsm_desafio03.core.util.now
import sv.edu.udb.dsm_desafio03.data.model.Resource
import sv.edu.udb.dsm_desafio03.data.repository.ResourceRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ResourceFormViewModel @Inject constructor(
    private val repo: ResourceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    /*
    * savedStateHandle contains navigation arguments:
    * - If resourceId exists, the item is obtained from the repository to edit it.
    * - If its null, the form will create a resource.
    */
    private val original: Resource? = savedStateHandle.get<String>("resourceId")?.let(repo::getResource)
    private val _uiState = MutableStateFlow(
        original?.let {
            ResourceFormState(
                title = it.title, type = it.type, author = it.author, code = it.code.orEmpty(),
                description = it.description, url = it.url, imageUrl = it.imageUrl, isEditing = true
            )
        } ?: ResourceFormState()
    )
    val uiState: StateFlow<ResourceFormState> = _uiState.asStateFlow()

    fun onTitleChange(v: String) = _uiState.update { it.copy(title = v) }
    fun onTypeChange(v: String) = _uiState.update { it.copy(type = v) }
    fun onAuthorChange(v: String) = _uiState.update { it.copy(author = v) }
    fun onCodeChange(v: String) = _uiState.update { it.copy(code = v) }
    fun onDescriptionChange(v: String) = _uiState.update { it.copy(description = v) }
    fun onUrlChange(v: String) = _uiState.update { it.copy(url = v) }
    fun onImageUrlChange(v: String) = _uiState.update { it.copy(imageUrl = v) }
    fun messageShown() = _uiState.update { it.copy(userMessage = null) }

    fun save() {
        val s = _uiState.value
        // Validation of required fields
        if (s.title.isBlank() || s.url.isBlank()) {
            _uiState.update { it.copy(userMessage = "Title and URL are required") }
            return
        }
        viewModelScope.launch {
            // Disables the button to prevent double taps
            _uiState.update { it.copy(isSaving = true) }
            try {
                val base = original
                if (base == null) {
                    repo.create(
                        Resource(
                            id = "", title = s.title, type = s.type, author = s.author,
                            code = s.code.ifBlank { null }, description = s.description,
                            url = s.url, imageUrl = s.imageUrl,
                            isFavorite = false, rating = 0,
                            createdAt = now(), lastModified = now()
                        )
                    )
                } else {
                    repo.update(
                        base.copy(
                            title = s.title, type = s.type, author = s.author,
                            code = s.code.ifBlank { null }, description = s.description,
                            url = s.url, imageUrl = s.imageUrl,
                            lastModified = now()
                        )
                    )
                }
                _uiState.update { it.copy(isSaving = false, saved = true) }
            } catch (e: CancellationException) { // Used by coroutines to cancel themselves
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false, userMessage = e.message ?: "Could not save") }
            }
        }
    }
}