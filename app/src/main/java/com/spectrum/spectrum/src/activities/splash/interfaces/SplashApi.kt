package com.spectrum.spectrum.src.activities.splash.interfaces

import com.spectrum.spectrum.src.customs.BaseResponse
import retrofit2.http.GET

interface SplashApi {

    @GET("/app/users/auth")
    suspend fun validateToken(): BaseResponse

}