package com.spectrum.spectrum.src.activities.main.fragments.post.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.post.models.DeletePostResponse
import com.spectrum.spectrum.src.activities.main.fragments.post.models.PostResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface PostApi {

    @GET("app/posts/{id}")
    suspend fun getPost(@Path("id") id: Int): PostResponse

    @PATCH("app/posts/{id}")
    suspend fun deleteMyPosts(@Path("id") id: Int): DeletePostResponse

}