package com.spectrum.spectrum.src.activities.main.fragments.company.models

data class IndustryResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<Industry>
)
