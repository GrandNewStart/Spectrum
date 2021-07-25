package com.spectrum.spectrum.src.activities.main.fragments.createSpec.models

import com.google.gson.annotations.SerializedName

data class Spec(
    var id: Int?,
    var age: Int?,
    var sex: String?,
    @SerializedName("jobGroupDtos") var jobGroups: ArrayList<JobGroup>?,
    var educations: ArrayList<EduItem>?,
    @SerializedName("expriences") var experiences: ArrayList<ExpItem>?,
    var licenses: ArrayList<LicItem>?,
    @SerializedName("etcs") var others: ArrayList<OtherSpec>?
)
