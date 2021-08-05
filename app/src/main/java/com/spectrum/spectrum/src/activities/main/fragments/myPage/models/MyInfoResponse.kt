package com.spectrum.spectrum.src.activities.main.fragments.myPage.models

import com.google.gson.annotations.SerializedName

data class MyInfoResponse (
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    val result: Result) {
    data class Result(
        var email: String,
        @SerializedName("username")var name: String
    )
}