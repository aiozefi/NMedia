package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val reposts: Int = 0,
    val repostByMe: Boolean = false,
    val video: String? = null
)