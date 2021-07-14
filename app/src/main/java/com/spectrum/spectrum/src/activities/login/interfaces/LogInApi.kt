package com.spectrum.spectrum.src.activities.login.interfaces

import com.spectrum.spectrum.src.activities.login.models.LogInResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInApi {

    @POST("/app/users/auth")
    suspend fun logIn(@Body body: RequestBody): LogInResponse

}