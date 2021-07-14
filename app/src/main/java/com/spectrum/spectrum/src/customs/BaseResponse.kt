package com.spectrum.spectrum.src.customs

data class BaseResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String
)