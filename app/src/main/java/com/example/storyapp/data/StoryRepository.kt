package com.example.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.example.storyapp.database.StoryDatabase
import com.example.storyapp.remote.apiService.ApiService
import com.example.storyapp.remote.response.ListStoryItem
import com.example.storyapp.remote.response.LoginResponse
import com.example.storyapp.remote.response.RegisterResponse
import com.example.storyapp.remote.response.ResponseAllStory
import com.example.storyapp.utils.ResourceData

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
) {
    fun getStory(token: String): LiveData<PagingData<ListStoryItem>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun loginUser(email: String, password: String): LiveData<ResourceData<LoginResponse?>> = liveData{
        emit(ResourceData.Loading)
        try{
            val result = apiService.login(email, password)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun registerUser(name: String, email: String, password: String): LiveData<ResourceData<RegisterResponse>> = liveData{
        emit(ResourceData.Loading)
        try{
            val result = apiService.registerUser(name, email, password)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

    fun getLocation(token: String): LiveData<ResourceData<ResponseAllStory>> = liveData {
        try{
            val result = apiService.getLocationUser("Bearer $token", 1)
            emit(ResourceData.Success(result))
        }catch (e: Exception){
            emit(ResourceData.Error(e))
        }
    }

}