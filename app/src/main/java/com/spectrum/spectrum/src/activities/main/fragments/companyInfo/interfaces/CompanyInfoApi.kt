package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models.CompanyResponse
import com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models.SpecResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CompanyInfoApi {

    @GET("/app/company-board/{id}")
    suspend fun getCompanyInfo(@Path("id") id: Int): CompanyResponse

    @POST("/app/company-board/user/{id}")
    suspend fun favoriteCompany(@Path("id") id: Int): BaseResponse

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

}