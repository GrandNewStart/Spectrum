package com.spectrum.spectrum.src.activities.main.fragments.search.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.search.models.SearchPostResponse
import retrofit2.http.*

interface SearchInterface {

    @GET("/app/home/search/{page}")
    suspend fun searchPost(@Path("page") page: Int, @Query("query", encoded = true) keyword: String): SearchPostResponse

}