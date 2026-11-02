package sv.edu.udb.dsm_desafio03.core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalDate
import sv.edu.udb.dsm_desafio03.R
import sv.edu.udb.dsm_desafio03.core.ui.theme.DSMDesafio03Theme
import sv.edu.udb.dsm_desafio03.data.model.Resource

@Composable
fun ItemResource(
    resource: Resource,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRatingChange: (Int) -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column {
            Row(modifier = Modifier.padding(8.dp, 8.dp, 8.dp, 0.dp)) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = resource.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight(700),
                    )
                    Text(
                        text = "$resource.type · $resource.author",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = resource.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                AsyncImage(
                    model = resource.imageUrl,
                    contentDescription = stringResource(R.string.icon_description_image),
                    placeholder = painterResource(R.drawable.ic_image),
                    error = painterResource(R.drawable.ic_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(12.dp, 0.dp, 0.dp, 0.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .size(100.dp)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconToggleButton(
                    checked = resource.isFavorite,
                    onCheckedChange = { onFavoriteClick() }
                ) {
                    Icon(
                        imageVector = if (resource.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.icon_description_like),
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
                RatingBar(rating = resource.rating, onRatingChange)
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.icon_description_edit),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.icon_description_delete),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

val sampleResource = Resource(
    id = "1", title = "A book", type = "Book", author = "Me", code = null,
    description = "This is a nice book. This is very true. I am not lying.",
    url = "https://books.org/1", imageUrl = "", isFavorite = false, rating = 3,
    createdAt = LocalDate(2025, 1, 1), lastModified = LocalDate(2025, 1, 2)
)

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ItemResourcePreview() {
    DSMDesafio03Theme {
        ItemResource(sampleResource, {}, {}, {}, {}, {})
    }
}