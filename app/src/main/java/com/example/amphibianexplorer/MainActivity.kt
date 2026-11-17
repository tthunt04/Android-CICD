
package com.example.amphibianexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amphibianexplorer.data.Amphibian
import com.example.amphibianexplorer.ui.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AmphibianExplorerApp()
            }
        }
    }
}

@Composable
fun AmphibianExplorerApp(
    animalViewModel: AmphibianViewModel = viewModel()
) {
    val currentState by animalViewModel.uiState
        .collectAsStateWithLifecycle()

    var selectedAnimal by remember {
        mutableStateOf<Amphibian?>(null)
    }

    BackHandler(enabled = selectedAnimal != null) {
        selectedAnimal = null
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = "Amphibian Explorer",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val animal = selectedAnimal

            if (animal != null) {

                AmphibianDetailScreen(
                    amphibian = animal,
                    onBack = { selectedAnimal = null }
                )

            } else {

                when (val state = currentState) {

                    AmphibianUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }

                    is AmphibianUiState.Success -> {
                        if (state.amphibians.isEmpty()) {
                            Text(
                                text = "No amphibians found.",
                                modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        } else {
                            AmphibianListScreen(
                                amphibians = state.amphibians,
                                onSelect = {
                                    selectedAnimal = it
                                }
                            )
                        }
                    }

                    is AmphibianUiState.Error -> {
                        Column(
                            modifier = Modifier.align(
                                Alignment.Center
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(state.message)

                            Button(
                                onClick = {
                                    animalViewModel.refreshAmphibians()
                                }
                            ) {
                                Text("Try Again")
                            }
                        }
                    }
                }
            }
        }
    }
}
