
package com.example.amphibianexplorer.network

import com.example.amphibianexplorer.data.Amphibian
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface AmphibianApi {

    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}

object NetworkClient {

    private const val BASE_URL =
        "https://android-kotlin-fun-mars-server.appspot.com/"

    val api: AmphibianApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AmphibianApi::class.java)
    }
}
