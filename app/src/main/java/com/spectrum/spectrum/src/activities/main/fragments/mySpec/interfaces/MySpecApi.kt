package com.spectrum.spectrum.src.activities.main.fragments.mySpec.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.mySpec.models.SpecResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MySpecApi {

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

    @PATCH("app/specs")
    suspend fun deleteMySpec(): BaseResponse

}