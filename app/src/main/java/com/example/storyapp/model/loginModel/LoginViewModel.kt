package com.example.storyapp.model.loginModel

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.StoryRepository

class LoginViewModel(private val repository: StoryRepository): ViewModel() {

    fun loginUser(email: String, password: String) = repository.loginUser(email, password)

}