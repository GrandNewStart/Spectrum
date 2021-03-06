package com.spectrum.spectrum.src.activities.main.fragments.myPage.interfaces

import com.spectrum.spectrum.src.activities.main.fragments.myPage.models.MyInfoResponse
import com.spectrum.spectrum.src.activities.main.fragments.myPage.models.SpecResponse
import com.spectrum.spectrum.src.customs.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface MyPageApi {

    @GET("/app/users/profile")
    suspend fun getMyInfo(): MyInfoResponse

    @GET("/app/specs")
    suspend fun getMySpec(): SpecResponse

}