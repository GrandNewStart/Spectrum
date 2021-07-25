package com.spectrum.spectrum.src.activities.main.fragments.createPost.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.createPost.models.PostResponse
import com.spectrum.spectrum.src.activities.main.fragments.createPost.models.SpecResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CreatePostApi {

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

    @POST("/app/posts")
    suspend fun uploadPost(@Body body: RequestBody): PostResponse

}