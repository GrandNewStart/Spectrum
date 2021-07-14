package com.spectrum.spectrum.src.activities.signup.interfaces

import com.spectrum.spectrum.src.activities.signup.models.SignUpResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpApi {

    @GET("/app/users/email")
    suspend fun checkEmail(@Query("search") search: String): BaseResponse

    @GET("/app/users/username")
    suspend fun checkNickname(@Query("search") search: String): BaseResponse

    @POST("/app/users")
    suspend fun signUp(@Body body: RequestBody): SignUpResponse

}