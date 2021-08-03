package com.spectrum.spectrum.src.activities.main.fragments.post.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.post.models.DeletePostResponse
import com.spectrum.spectrum.src.activities.main.fragments.post.models.GetCommentResponse
import com.spectrum.spectrum.src.activities.main.fragments.post.models.PostResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface PostApi {

    @GET("app/posts/{id}")
    suspend fun getPost(@Path("id") id: Int): PostResponse

    @PATCH("app/posts/{id}")
    suspend fun deleteMyPosts(@Path("id") id: Int): DeletePostResponse

    @GET("/app/comments/{id}")
    suspend fun getComments(@Path("id") id: Int): GetCommentResponse

    @POST("/app/comments/{id}")
    suspend fun postComment(@Path("id") id: Int, @Body body: RequestBody): BaseResponse

}