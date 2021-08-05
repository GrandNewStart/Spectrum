package com.spectrum.spectrum.src.activities.main.fragments.myPost.models

data class MyPostResponse (
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<Post>
)