
package com.example.amphibianexplorer.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.amphibianexplorer.data.Amphibian

@Composable
fun AmphibianListScreen(
    amphibians: List<Amphibian>,
    onSelect: (Amphibian) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(amphibians) { animal ->
            AmphibianCard(
                animal = animal,
                onSelect = { onSelect(animal) }
            )
        }
    }
}

@Composable
fun AmphibianCard(
    animal: Amphibian,
    onSelect: () -> Unit
) {
    var expanded by remember(animal.name) {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = animal.imageUrl,
                contentDescription = animal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = animal.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = animal.type,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            TextButton(
                onClick = { expanded = !expanded }
            ) {
                Text(
                    if (expanded) "Hide preview"
                    else "Show preview"
                )
            }

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = animal.description.take(150) + "...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = onSelect,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Details")
            }
        }
    }
}
