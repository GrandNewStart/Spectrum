package com.spectrum.spectrum.src.activities.main.fragments.editpost.models

data class Post(
    var postId: Int?,
    var username: String?,
    var jobStatus: JobStatus?,
    var category: Category?,
    var createdAt: String?,
    var title: String?,
    var content: String?,
    var spec: Spec?
) {
    data class JobStatus(
        var id: String?,
        var data: String?
    )
    data class Category(
        var id: String?,
        var data: String?
    )
}