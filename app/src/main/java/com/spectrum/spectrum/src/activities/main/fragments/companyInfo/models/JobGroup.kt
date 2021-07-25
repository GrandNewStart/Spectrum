package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models

import com.google.gson.annotations.SerializedName

data class JobGroup(
    var id: Int,
    @SerializedName("data") var name: String,
    var selectIndex: Int = 0
)
