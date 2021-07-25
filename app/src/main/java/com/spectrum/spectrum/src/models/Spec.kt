package com.spectrum.spectrum.src.models

data class Spec(
    var id: String,
    var userImage: String,
    var userName: String,
    var update: String,
    var userInfo: ArrayList<Info>,
    var educations: ArrayList<Education>,
    var experiences: ArrayList<Experience>,
    var licenses: ArrayList<License>,
    var otherSpecs: String
)
