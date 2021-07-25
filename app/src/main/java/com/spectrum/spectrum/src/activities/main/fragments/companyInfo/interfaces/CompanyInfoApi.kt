package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models.SpecResponse
import retrofit2.http.GET

interface CompanyInfoApi {

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

}