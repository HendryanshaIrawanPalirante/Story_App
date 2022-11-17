package com.example.storyapp.data

import com.example.storyapp.remote.apiService.ApiService
import com.example.storyapp.remote.response.AddStoryResponse
import com.example.storyapp.remote.response.LoginResponse
import com.example.storyapp.remote.response.RegisterResponse
import com.example.storyapp.remote.response.ResponseAllStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class FakeApiService: ApiService {

    private val dummyStoryResponse = DataDummy.generateDummyStoriesResponse()
    private val dummyLogin = DataDummy.loginResponseSuccess()
    private val dummyRegister = DataDummy.registerResponseSuccess()
    private val dummyLocation = DataDummy.generateDummyMaps()

    override suspend fun login(email: String, password: String): LoginResponse {
        return dummyLogin
    }

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {
        return dummyRegister
    }

    override suspend fun getStories(token: String, page: Int, size: Int): ResponseAllStory {
        return dummyStoryResponse
    }

    override fun uploadImage(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ): Call<AddStoryResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationUser(token: String, location: Int): ResponseAllStory {
        return dummyLocation
    }

}