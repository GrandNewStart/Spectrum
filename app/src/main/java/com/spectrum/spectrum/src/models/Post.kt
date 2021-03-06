package com.spectrum.spectrum.src.models

data class Post(
    var id: Int,
    var title: String,
    var date: String,
    var commentCount: Int,
    var markCount: Int,
    var infos: ArrayList<Info>
)
