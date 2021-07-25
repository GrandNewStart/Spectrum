package com.spectrum.spectrum.src.activities.main.fragments.createPost.models

import com.google.gson.annotations.SerializedName

data class Spec(
    var id: Int,
    var age: Int,
    var sex: String,
    var username: String,
    var updatedAt: String,
    @SerializedName("jobGroupDtos") var jobGroups: ArrayList<JobGroup>,
    var educations: ArrayList<Education>,
    var experiences: ArrayList<Experience>,
    var licenses: ArrayList<License>,
    @SerializedName("etcs") var others: ArrayList<OtherSpec>
)
