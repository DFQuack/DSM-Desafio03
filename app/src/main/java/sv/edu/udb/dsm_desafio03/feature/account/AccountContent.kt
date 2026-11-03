package sv.edu.udb.dsm_desafio03.feature.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sv.edu.udb.dsm_desafio03.R
import sv.edu.udb.dsm_desafio03.core.ui.components.ResourceList
import sv.edu.udb.dsm_desafio03.data.model.Resource

@Composable
fun AccountContent(
    uiState: AccountUiState,
    onResourceClick: (Resource) -> Unit,
    onFavoriteClick: (Resource) -> Unit,
    onRatingChange: (Resource, Int) -> Unit,
    onEditClick: (Resource) -> Unit,
    onDeleteConfirmed: (Resource) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(8.dp)) {
        Icon(
            Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(72.dp).align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.account_favorites_title, uiState.favorites.size),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        if (uiState.favorites.isEmpty()) {
            Text(stringResource(R.string.account_no_favorites))
        } else {
            ResourceList(
                resources = uiState.favorites,
                onResourceClick = onResourceClick,
                onFavoriteClick = onFavoriteClick,
                onRatingChange = onRatingChange,
                onEditClick = onEditClick,
                onDeleteConfirmed = onDeleteConfirmed,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}