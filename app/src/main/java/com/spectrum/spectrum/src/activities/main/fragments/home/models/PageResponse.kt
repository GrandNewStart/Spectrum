package com.spectrum.spectrum.src.activities.main.fragments.home.models

data class PageResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<Post>
)