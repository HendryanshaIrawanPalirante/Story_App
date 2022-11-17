package com.example.storyapp.data

import android.content.Context
import com.example.storyapp.database.StoryDatabase
import com.example.storyapp.remote.apiService.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository{
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}