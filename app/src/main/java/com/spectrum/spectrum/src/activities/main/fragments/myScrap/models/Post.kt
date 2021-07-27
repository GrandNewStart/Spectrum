package com.spectrum.spectrum.src.activities.main.fragments.myScrap.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postId") var id: Int,
    var title: String,
    var createdAt: String,
    var jobStatus: String,
    var age: Int,
    var sex: String,
    var jobGroupList: ArrayList<JobGroup>
)
