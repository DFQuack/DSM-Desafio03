package sv.edu.udb.dsm_desafio03.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import sv.edu.udb.dsm_desafio03.core.util.now
import sv.edu.udb.dsm_desafio03.data.model.Resource
import sv.edu.udb.dsm_desafio03.data.repository.ResourceRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: ResourceRepository
) : ViewModel() {
    private val userMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<AccountUiState> = combine(
        repo.resources,
        userMessage
    ) { list, message ->
        AccountUiState(favorites = list.filter { it.isFavorite }, userMessage = message)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), AccountUiState())

    fun onFavoriteClick(r: Resource) = run {
        repo.update(r.copy(isFavorite = !r.isFavorite, lastModified = now()))
    }
    fun onRatingChange(r: Resource, rating: Int) = run {
        repo.update(r.copy(rating = rating, lastModified = now()))
    }
    fun onDelete(r: Resource) = run { repo.delete(r.id) }
    fun messageShown() { userMessage.value = null }

    private fun run(block: suspend () -> Unit) {
        viewModelScope.launch {
            try { block() }
            catch (e: CancellationException) { throw e }
            catch (e: Exception) { userMessage.value = e.message ?: "Something went wrong" }
        }
    }
}