package com.spectrum.spectrum.src.activities.main.fragments.createSpec.models
import com.google.gson.annotations.SerializedName

data class LicItem(
    @SerializedName("licenseId") var id: Int?,
    @SerializedName("certification") var name: String?,
    var score: String?
)
