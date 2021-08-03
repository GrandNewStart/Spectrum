package com.spectrum.spectrum.src.activities.main.models

import com.google.gson.annotations.SerializedName

data class RecentPost(
    @SerializedName("postId") var id: Int,
    var title: String,
    var createdAt: String,
    var jobStatus: String,
    var age: Int,
    var sex: String,
    var jobGroupList: ArrayList<JobGroup>
)
