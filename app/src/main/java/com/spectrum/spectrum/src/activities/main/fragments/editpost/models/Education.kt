package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

import com.google.gson.annotations.SerializedName

data class Education(
    @SerializedName("eduId") var id: Int,
    var location: Location,
    var graduate: Graduate,
    var degree: Degree,
    var grade: Double,
    @SerializedName("schoolName") var school: String,
    var major: String
)
