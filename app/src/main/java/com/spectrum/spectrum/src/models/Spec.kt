package com.spectrum.spectrum.src.models

data class Spec(
    var id: String,
    var userName: String,
    var update: String,
    var userInfo: ArrayList<Info>,
    var educations: ArrayList<Education>,
    var experiences: ArrayList<Experience>,
    var certifications: ArrayList<License>,
    var otherSpecs: String
)
