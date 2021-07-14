package com.spectrum.spectrum.src.models

data class Post(
    var id: String,
    var title: String,
    var date: String,
    var commentCount: Int,
    var markCount: Int,
    var specs: ArrayList<Spec>
)
