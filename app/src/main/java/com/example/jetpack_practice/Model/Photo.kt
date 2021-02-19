package com.example.jetpack_practice.Model

import java.io.Serializable

data class Photo(
    var thumbnail: String?,
    var authodr: String?,
    var createdAt: String?,
    var likeCount: Int?
) : Serializable //직렬화 가능
