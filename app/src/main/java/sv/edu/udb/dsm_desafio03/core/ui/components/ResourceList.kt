package sv.edu.udb.dsm_desafio03.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sv.edu.udb.dsm_desafio03.R
import sv.edu.udb.dsm_desafio03.data.model.Resource

@Composable
fun ResourceList(
    resources: List<Resource>,
    onResourceClick: (Resource) -> Unit,
    onFavoriteClick: (Resource) -> Unit,
    onRatingChange: (Resource, Int) -> Unit,
    onEditClick: (Resource) -> Unit,
    onDeleteConfirmed: (Resource) -> Unit,
    modifier: Modifier = Modifier
) {
    var pendingDelete by remember { mutableStateOf<Resource?>(null) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(resources, key = {it.id}) { resource ->
            ItemResource(
                resource = resource,
                onClick = { onResourceClick(resource) },
                onFavoriteClick = { onFavoriteClick(resource) },
                onRatingChange = { onRatingChange(resource, it) },
                onEditClick = { onEditClick(resource) },
                onDeleteClick = { pendingDelete = resource }
            )
        }
    }

    // Manages the AlertDialog after a user clicks delete on a resource
    pendingDelete?.let { resource ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text(stringResource(R.string.delete_title)) },
            text = { Text(stringResource(R.string.delete_message, resource.title)) },
            confirmButton = {
                TextButton(onClick = { onDeleteConfirmed(resource); pendingDelete = null }) {
                    Text(stringResource(R.string.delete_confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}