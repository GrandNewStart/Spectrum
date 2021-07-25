package com.spectrum.spectrum.src.activities.main.fragments.editSpec.models

data class SpecResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Spec
)