package com.example.storyapp.data

import com.example.storyapp.model.userModel.UserModel
import com.example.storyapp.remote.response.*

object DataDummy {
    private fun  generateDummyLoginResult() : LoginResult {
        return LoginResult(
            "umar",
            "1234",
            "token"
        )
    }

    fun loginResponseSuccess(): LoginResponse {
        return LoginResponse(
            loginResult =  generateDummyLoginResult(),
            error = false,
            message = "Success"
        )
    }

    fun registerResponseSuccess(): RegisterResponse {
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyStoriesResponse(): ResponseAllStory {
        return ResponseAllStory(generateDummyListStory(), false, "Success")
    }

    fun generateDummyListStory(): List<ListStoryItem> {
        val stories = arrayListOf<ListStoryItem>()

        for (i in 0 until 10) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "2022-01-08T06:34:18.598Z",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return stories
    }

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                "2022",
                "description $i",
                "$i",
                "name $i",
                "url",
                10.10,
                10.10
            )
            items.add(quote)
        }
        return items
    }

    fun generateDummyMaps(): ResponseAllStory{
        val stories = arrayListOf<ListStoryItem>()

        for (i in 0 until 10) {
            val story = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "2022-01-08T06:34:18.598Z",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "Lorem Ipsum",
                -16.002,
                -10.212
            )

            stories.add(story)
        }
        return ResponseAllStory(stories, false, "Stories fetched successfully")
    }

    fun generateDummyUser(): UserModel{
        return UserModel(
            "1",
            "Ian",
            "2nsgr948aoh",
            true
        )
    }

}