package com.spectrum.spectrum.src.activities.main.fragments.createPost.models

data class SpecResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Spec
)