package com.spectrum.spectrum.src.activities.main.fragments.editSpec.models

import com.google.gson.annotations.SerializedName

data class OtherSpec (
    @SerializedName("etcId") var id: Int,
    var content: String
)