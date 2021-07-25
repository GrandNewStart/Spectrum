package com.spectrum.spectrum.src.activities.main.fragments.createPost.models

data class PostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(var postId: Int)
}
