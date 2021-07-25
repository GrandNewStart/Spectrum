package com.spectrum.spectrum.src.activities.main.fragments.createSpec.interfaces

import com.spectrum.spectrum.src.customs.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface CreateSpecApi {

    @POST("/app/specs")
    suspend fun uploadMySpecs(@Body body: RequestBody): BaseResponse

}