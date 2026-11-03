package sv.edu.udb.dsm_desafio03.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.debounce
import sv.edu.udb.dsm_desafio03.R

@Composable
fun SearchBar(
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
    ) {
    val textState = rememberTextFieldState()
    val currentOnQueryChange by rememberUpdatedState(onQueryChange)

    // Every edit emits the new text as an update
    LaunchedEffect(textState) {
        snapshotFlow { textState.text.toString() }
            .collect { currentOnQueryChange(it) }
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            state = textState,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.icon_description_search)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.outline,
                focusedTrailingIconColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_hint)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}