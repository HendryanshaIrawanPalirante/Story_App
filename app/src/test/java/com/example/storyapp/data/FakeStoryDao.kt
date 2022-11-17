package com.example.storyapp.data

import androidx.paging.PagingSource
import com.example.storyapp.database.StoryDao
import com.example.storyapp.remote.response.ListStoryItem

class FakeStoryDao: StoryDao {

    private var storyData = mutableListOf<List<ListStoryItem>>()

    override suspend fun insertStory(quote: List<ListStoryItem>) {
        storyData.add(quote)
    }

    override fun getAllStory(): PagingSource<Int, ListStoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        storyData.clear()
    }
}