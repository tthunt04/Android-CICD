package com.example.amphibianexplorer.data

import com.google.gson.annotations.SerializedName

data class Amphibian(
    val name: String,
    val type: String,
    val description: String,
    @SerializedName("img_src")
    val imageUrl: String?
)
