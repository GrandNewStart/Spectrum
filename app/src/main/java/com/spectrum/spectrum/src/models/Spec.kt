package com.spectrum.spectrum.src.models

data class Spec(
    var id: String,
    var userName: String,
    var title: String,
    var update: String,
    var userInfo: ArrayList<Info>,
    var educations: ArrayList<EduItem>,
    var experiences: ArrayList<ExpItem>,
    var certifications: ArrayList<CertItem>,
    var otherSpecs: String
)
