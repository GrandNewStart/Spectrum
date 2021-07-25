package com.spectrum.spectrum.src.activities.main.fragments.post.models

data class PostResponse(
    var isSuccess: Boolean,
    var code: Int,
    var message: String,
    var result: Result
) {
    data class Result(
        var postId: Int?,
        var mine: Boolean?,
        var username: String?,
        var jobStatus: JobStatus?,
        var category: Category?,
        var createdAt: String?,
        var title: String?,
        var content: String?,
        var spec: Spec?
    )
    data class JobStatus(
        var id: String?,
        var data: String?
    )
    data class Category(
        var id: String?,
        var data: String?
    )
}
