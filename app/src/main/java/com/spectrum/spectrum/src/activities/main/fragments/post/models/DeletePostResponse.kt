package com.spectrum.spectrum.src.activities.main.fragments.post.models

data class DeletePostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(var postId: Int)
}