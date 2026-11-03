package sv.edu.udb.dsm_desafio03.feature.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sv.edu.udb.dsm_desafio03.R

@Composable
fun ResourceFormContent(
    uiState: ResourceFormState,
    onTitleChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onUrlChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(if (uiState.isEditing) R.string.form_edit_title else R.string.form_add_title),
            style = MaterialTheme.typography.headlineMedium
        )
        FormField(stringResource(R.string.field_title), uiState.title, onTitleChange)
        FormField(stringResource(R.string.field_type), uiState.type, onTypeChange)
        FormField(stringResource(R.string.field_author), uiState.author, onAuthorChange)
        FormField(stringResource(R.string.field_code), uiState.code, onCodeChange)
        FormField(stringResource(R.string.field_description), uiState.description, onDescriptionChange)
        FormField(stringResource(R.string.field_url), uiState.url, onUrlChange)
        FormField(stringResource(R.string.field_image_url), uiState.imageUrl, onImageUrlChange)
        Button(onClick = onSave, enabled = !uiState.isSaving, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.save))
        }
    }
}

@Composable
private fun FormField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}