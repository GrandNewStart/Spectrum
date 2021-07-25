package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

import com.google.gson.annotations.SerializedName

data class JobGroup(
    @SerializedName("jobGroupId") var id: Int,
    var name: String
)
