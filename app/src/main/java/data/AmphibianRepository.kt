package com.example.amphibianexplorer.data

import com.example.amphibianexplorer.network.AmphibianApi

class AmphibianRepository(
    private val api: AmphibianApi
) {
    suspend fun loadAmphibians(): List<Amphibian> {
        return api.getAmphibians()
    }
}
