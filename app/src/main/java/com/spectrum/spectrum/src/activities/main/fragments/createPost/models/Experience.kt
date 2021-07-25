package com.spectrum.spectrum.src.activities.main.fragments.createPost.models

import com.google.gson.annotations.SerializedName

data class Experience(
    @SerializedName("expId") var id: Int,
    @SerializedName("companyName") var company: String,
    var jobGroup: String,
    var jobPosition: String,
    @SerializedName("jobStart") var startDate: String,
    @SerializedName("jobEnd") var endDate: String
)
