package com.spectrum.spectrum.src.activities.main.fragments.home.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.home.models.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @GET("/app/home/{page}")
    suspend fun getHomePage(@Path("page") page: Int): PageResponse

    @GET("/app/home/{page}")
    suspend fun getHomePage(@Path("page") page: Int,
                            @Query("filter1") jobGroup1: Int): PageResponse

    @GET("/app/home/{page}")
    suspend fun getHomePage(@Path("page") page: Int,
                            @Query("filter1") jobGroup1: Int,
                            @Query("filter2") jobGroup2: Int): PageResponse

}