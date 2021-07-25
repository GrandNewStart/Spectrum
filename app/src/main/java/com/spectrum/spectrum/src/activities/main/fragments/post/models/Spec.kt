package com.spectrum.spectrum.src.activities.main.fragments.post.models

import com.google.gson.annotations.SerializedName

data class Spec(
    var id: Int,
    var age: Int,
    var sex: String,
    var userName: String,
    var updatedAt: String,
    @SerializedName("jobGroupDtos") var jobGroups: ArrayList<JobGroup>,
    var educations: ArrayList<Education>,
    @SerializedName("expriences") var experiences: ArrayList<Experience>,
    var licenses: ArrayList<License>,
    @SerializedName("etcs") var otherSpecs: ArrayList<OtherSpec>

)
