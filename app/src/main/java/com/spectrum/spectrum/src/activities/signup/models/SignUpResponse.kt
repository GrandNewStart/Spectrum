package com.spectrum.spectrum.src.activities.signup.models

data class SignUpResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(
        var jwt: String,
        var userIdx: Int
    )
}
