package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

data class EditPostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(var postId: Int)
}
