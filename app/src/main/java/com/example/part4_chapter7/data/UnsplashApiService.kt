package com.example.part4_chapter7.data

import com.example.part4_chapter7.BuildConfig
import com.example.part4_chapter7.data.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("photos/random?" +
            "client_id=${BuildConfig.UNSPLASH_ACESS_KEY}" +
            "&count=30")
    suspend fun getRandomPhotos(
        @Query("query") query: String?
    ):Response<List<PhotoResponse>>

}