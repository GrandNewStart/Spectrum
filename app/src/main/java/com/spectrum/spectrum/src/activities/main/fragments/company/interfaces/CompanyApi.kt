package com.spectrum.spectrum.src.activities.main.fragments.company.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.company.models.CompanyResponse
import com.spectrum.spectrum.src.activities.main.fragments.company.models.IndustryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyApi {

    @GET("/app/datas/companygroup")
    suspend fun getIndustries(): IndustryResponse

    @GET("/app/company-board")
    suspend fun getCompanyPage(@Query("pagenum") page: Int, @Query("filter") filter: Int? = null): CompanyResponse

}