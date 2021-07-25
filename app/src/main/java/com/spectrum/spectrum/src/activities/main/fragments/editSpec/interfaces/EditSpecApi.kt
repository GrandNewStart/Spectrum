package com.spectrum.spectrum.src.activities.main.fragments.editSpec.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.editSpec.models.SpecResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface EditSpecApi {

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

    @PUT("/app/specs")
    suspend fun updateMySpecs(@Body body: RequestBody): BaseResponse

}