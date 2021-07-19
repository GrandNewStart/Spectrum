package com.spectrum.spectrum.src.models

data class Evaluation(
    var id: Int,
    var name: String,
    var count: Int,
    var isSelected: Boolean = false
)
