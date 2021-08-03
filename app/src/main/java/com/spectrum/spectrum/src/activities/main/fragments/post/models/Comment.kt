package com.spectrum.spectrum.src.activities.main.fragments.post.models

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("commentId") var id: Int,
    @SerializedName("commentName") var name: String,
    @SerializedName("commentCnt") var count: Int,
    var isSelected: Boolean = false
)