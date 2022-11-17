package com.example.storyapp.model.mainModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.response.ListStoryItem

class StoryViewModel(private val repository: StoryRepository): ViewModel() {

    fun listStory(token: String): LiveData<PagingData<ListStoryItem>> = repository.getStory(token).cachedIn(viewModelScope)

}