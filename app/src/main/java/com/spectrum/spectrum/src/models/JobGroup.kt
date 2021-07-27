package com.spectrum.spectrum.src.models

import com.google.gson.annotations.SerializedName

data class JobGroup(
    var id: Int,
    @SerializedName("data") var name: String,
)
