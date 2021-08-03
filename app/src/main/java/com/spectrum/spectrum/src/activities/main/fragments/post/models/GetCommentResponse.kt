package com.spectrum.spectrum.src.activities.main.fragments.post.models

import com.google.gson.annotations.SerializedName

data class GetCommentResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(
        var topComments: ArrayList<Comment>,
        var eduComments: ArrayList<Comment>,
        var expComments: ArrayList<Comment>,
        var licComments: ArrayList<Comment>,
        var etcComments: ArrayList<Comment>,
        @SerializedName("mineCommentIds") var myComments: ArrayList<Int>
    )
}
