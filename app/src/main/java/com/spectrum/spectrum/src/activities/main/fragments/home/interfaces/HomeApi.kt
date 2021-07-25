package com.spectrum.spectrum.src.activities.main.fragments.home.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.home.models.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeApi {

    @GET("/app/home/{page}")
    suspend fun getHomePage(@Path("page") page: Int): PageResponse

}