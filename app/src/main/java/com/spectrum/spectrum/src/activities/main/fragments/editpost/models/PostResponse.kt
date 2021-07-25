package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

data class PostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Post
)