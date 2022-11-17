package com.example.storyapp.model.mapsModel

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.StoryRepository

class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    fun getLocation(token: String) = repository.getLocation(token)

}