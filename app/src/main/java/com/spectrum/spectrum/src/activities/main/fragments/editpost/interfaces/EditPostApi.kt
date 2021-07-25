package com.spectrum.spectrum.src.activities.main.fragments.editpost.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.editpost.models.EditPostResponse
import com.spectrum.spectrum.src.activities.main.fragments.editpost.models.PostResponse
import com.spectrum.spectrum.src.activities.main.fragments.editpost.models.SpecResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditPostApi {

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

    @GET("app/posts/{id}")
    suspend fun getMyPost(@Path("id") id: Int): PostResponse

    @PUT("/app/posts/{id}")
    suspend fun editMyPost(@Path("id") id: Int, @Body body: RequestBody): EditPostResponse

}