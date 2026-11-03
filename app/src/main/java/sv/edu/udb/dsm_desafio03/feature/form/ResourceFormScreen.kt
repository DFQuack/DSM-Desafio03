package sv.edu.udb.dsm_desafio03.feature.form

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ResourceFormScreen(
    onNavigateBack: () -> Unit,
    viewModel: ResourceFormViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle() // UI recomposes on every change
    val snackbarHostState = remember { SnackbarHostState() }

    // After successfully saving, the app exits the form.
    LaunchedEffect(uiState.saved) { if (uiState.saved) onNavigateBack() }

    //Shows a Snackbar when userMessage is non-null
    uiState.userMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            viewModel.messageShown()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        ResourceFormContent(
            uiState = uiState,
            onTitleChange = viewModel::onTitleChange,
            onTypeChange = viewModel::onTypeChange,
            onAuthorChange = viewModel::onAuthorChange,
            onCodeChange = viewModel::onCodeChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onUrlChange = viewModel::onUrlChange,
            onImageUrlChange = viewModel::onImageUrlChange,
            onSave = viewModel::save,
            modifier = Modifier.padding(padding)
        )
    }
}