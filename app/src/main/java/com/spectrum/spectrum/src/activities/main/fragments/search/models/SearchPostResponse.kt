package com.spectrum.spectrum.src.activities.main.fragments.search.models

data class SearchPostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<Post>
)