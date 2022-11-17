package com.example.storyapp.model.userModel

data class UserModel(
    val userId: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
)