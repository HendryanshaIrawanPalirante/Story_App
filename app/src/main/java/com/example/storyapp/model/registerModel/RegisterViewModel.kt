package com.example.storyapp.model.registerModel

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.StoryRepository

class RegisterViewModel(private val repository: StoryRepository): ViewModel() {

    fun registerUser(name: String, email: String, password: String) = repository.registerUser(name, email, password)

}