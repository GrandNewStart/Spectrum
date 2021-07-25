package com.spectrum.spectrum.src.activities.main.fragments.myPage.models

import com.google.gson.annotations.SerializedName

data class Spec(
    var age: Int?,
    var sex: String?,
    var username: String,
    var updatedAt: String,
    @SerializedName("jobGroupDtos") var jobGroups: ArrayList<JobGroup>?,
)
