package com.example.addressautocomplete

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotonService {
    @GET("/api")
    suspend fun searchAddresses(@Query("q") query: String): PhotonResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://photon.komoot.io"

    val api: PhotonService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotonService::class.java)
    }
}
