package sv.edu.udb.dsm_desafio03.feature.resources

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sv.edu.udb.dsm_desafio03.R

@Composable
fun ResourceScreen(
    onAddClick: () -> Unit,
    onEditClick: (String) -> Unit,
    viewModel: ResourceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val uriHandler = LocalUriHandler.current

    uiState.userMessage?.let { message ->
        LaunchedEffect(message) {
            snackbarHostState.showSnackbar(message)
            viewModel.messageShown()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.icon_description_add))
            }
        }
    ) { padding ->
        ResourceContent(
            uiState = uiState,
            onQueryChange = viewModel::onQueryChange,
            onResourceClick = { resource ->
                // Opening a URL is a UI concern, so it lives here and not in the ViewModel
                runCatching { uriHandler.openUri(resource.url) }
            },
            onFavoriteClick = viewModel::onFavoriteClick,
            onRatingChange = viewModel::onRatingChange,
            onEditClick = { onEditClick(it.id) },
            onDeleteConfirmed = viewModel::onDelete,
            modifier = Modifier.padding(padding)
        )
    }
}