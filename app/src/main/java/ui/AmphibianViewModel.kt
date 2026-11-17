
package com.example.amphibianexplorer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibianexplorer.data.Amphibian
import com.example.amphibianexplorer.data.AmphibianRepository
import com.example.amphibianexplorer.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface AmphibianUiState {
    data object Loading : AmphibianUiState

    data class Success(
        val amphibians: List<Amphibian>
    ) : AmphibianUiState

    data class Error(
        val message: String
    ) : AmphibianUiState
}

class AmphibianViewModel : ViewModel() {

    private val repository = AmphibianRepository(
        NetworkClient.api
    )

    private val _uiState =
        MutableStateFlow<AmphibianUiState>(
            AmphibianUiState.Loading
        )

    val uiState: StateFlow<AmphibianUiState> = _uiState

    init {
        refreshAmphibians()
    }

    fun refreshAmphibians() {
        viewModelScope.launch {
            _uiState.value = AmphibianUiState.Loading

            try {
                val results = repository.loadAmphibians()

                _uiState.value =
                    AmphibianUiState.Success(results)

            } catch (error: Exception) {
                _uiState.value = AmphibianUiState.Error(
                    "Unable to load amphibians. Check your internet connection and try again."
                )
            }
        }
    }
}
