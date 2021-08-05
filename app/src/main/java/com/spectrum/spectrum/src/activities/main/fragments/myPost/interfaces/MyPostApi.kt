package com.spectrum.spectrum.src.activities.main.fragments.myPost.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.myPost.models.MyPostResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPostApi {

    @GET("/app/posts/user/{page}")
    suspend fun getMyPosts(@Path("page") page: Int): MyPostResponse

}