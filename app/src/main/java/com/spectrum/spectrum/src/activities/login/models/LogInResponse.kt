package com.spectrum.spectrum.src.activities.login.models

data class LogInResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(
        var jwt: String,
        var userIdx: Int,
        var hasSpec: String
    )
}
