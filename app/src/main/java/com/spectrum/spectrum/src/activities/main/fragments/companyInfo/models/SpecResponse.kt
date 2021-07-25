package com.spectrum.spectrum.src.activities.main.fragments.companyInfo.models

import com.google.gson.annotations.SerializedName
import com.spectrum.spectrum.src.activities.main.fragments.createPost.models.JobGroup

data class SpecResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Spec
) {
    data class Spec(
        var age: Int,
        var sex: String,
        var username: String,
        var updatedAt: String,
        @SerializedName("jobGroupDtos") var jobGroups: ArrayList<JobGroup>,
    )
}