package com.spectrum.spectrum.src.activities.main.fragments.company.models

import com.google.gson.annotations.SerializedName

data class Company(
    @SerializedName("companyId") var id: Int,
    @SerializedName("companyName") var name: String,
    @SerializedName("companyGroup") var industry: String,
    @SerializedName("specCnt") var specCount: Int,
    @SerializedName("mine") var isMine: String
)
