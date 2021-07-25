package com.spectrum.spectrum.src.activities.main.fragments.editSpec.models

import com.google.gson.annotations.SerializedName

data class EduItem(
    @SerializedName("eduId") var id: Int?,
    var location: Location?,
    var graduate: Graduate?,
    var degree: Degree?,
    var grade: Double?,
    @SerializedName("schoolName") var school: String?,
    var major: String?
)
