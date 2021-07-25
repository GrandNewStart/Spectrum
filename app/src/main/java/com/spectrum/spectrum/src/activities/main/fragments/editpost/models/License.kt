package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("licenseId") var id: Int,
    @SerializedName("certification") var name: String,
    var score: String
)
