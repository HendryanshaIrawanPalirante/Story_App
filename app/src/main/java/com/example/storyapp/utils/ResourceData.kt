package com.example.storyapp.utils

sealed class ResourceData<out R> private constructor() {
    data class Success<out T>(val data: T?): ResourceData<T>()
    data class Error(val message: Exception): ResourceData<Nothing>()
    object Loading: ResourceData<Nothing>()
}