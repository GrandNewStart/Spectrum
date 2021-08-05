package com.spectrum.spectrum.src.activities.main.fragments.company.models

data class CompanyResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: ArrayList<Company>
)
