package sv.edu.udb.dsm_desafio03.feature.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sv.edu.udb.dsm_desafio03.R
import sv.edu.udb.dsm_desafio03.core.util.now
import sv.edu.udb.dsm_desafio03.data.model.Resource
import sv.edu.udb.dsm_desafio03.data.repository.ResourceRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/*
* Analogous to a controller: middle layer between model and views.
*/
@HiltViewModel
class ResourceViewModel @Inject constructor(
    private val repo: ResourceRepository
) : ViewModel() {
    private val query = MutableStateFlow("")
    private val isLoading = MutableStateFlow(true)
    private val userMessage = MutableStateFlow<String?>(null)

    // Rebuilds the state whenever any of the flows update (repository change, typing in the search bar, request starts or ends).
    val uiState: StateFlow<ResourceUiState> = combine(
        repo.resources,
        query,
        isLoading,
        userMessage
    ) { list, q, loading, message ->
            ResourceUiState(
                resources = list.filter {
                    it.title.contains(q, ignoreCase = true) || it.author.contains(
                        q,
                        ignoreCase = true
                    )
                },
                query = q,
                isLoading = loading,
                userMessage = message
            )
        }.stateIn(
            viewModelScope,
            // Grace period of 5 seconds so the flow doesn't reset after a rotation.
            SharingStarted.WhileSubscribed(5_000),
            ResourceUiState(isLoading = true)
        )

    init { refresh() }
    fun refresh() = runAction { repo.refresh() }
    fun onQueryChange(value: String) { query.value = value }

    // Copy is used because Resource is immutable. It sends a modified copy to the repository.
    fun onFavoriteClick(r: Resource) = runAction {
        repo.update(r.copy(isFavorite = !r.isFavorite, lastModified = now()))
    }
    fun onRatingChange(r: Resource, rating: Int) = runAction {
        repo.update(r.copy(rating = rating, lastModified = now()))
    }
    fun onDelete(r: Resource) = runAction { repo.delete(r.id) }

    // After a Snackbar is shown, this is called so the message doesn't show again.
    fun messageShown() { userMessage.value = null }

    private fun runAction(block: suspend () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                block() // Runs the action
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                userMessage.value = e.message ?: R.string.error_message_generic.toString()
            } finally {
                isLoading.value = false
            }
        }
    }
}