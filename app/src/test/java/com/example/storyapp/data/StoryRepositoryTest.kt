package com.example.storyapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.room.Room
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.PagedTest
import com.example.storyapp.database.StoryDao
import com.example.storyapp.database.StoryDatabase
import com.example.storyapp.remote.apiService.ApiService
import com.example.storyapp.remote.response.ListStoryItem
import com.example.storyapp.utils.ResourceData
import com.example.storyapp.utils.getOrAwaitValue
import com.example.storyapp.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var apiService: ApiService
    private lateinit var storyDatabase: StoryDatabase
    private lateinit var storyDao: StoryDao
    private val context = mock(Context::class.java)
    private val dummyEmail = "hend@gmail.com"
    private val dummyPassword = "123456789"

    @Before
    fun setup(){
        apiService = FakeApiService()
        storyDao = FakeStoryDao()
        storyDatabase = Room.inMemoryDatabaseBuilder(context, StoryDatabase::class.java).build()
        storyRepository = StoryRepository(storyDatabase, apiService)
    }


    @Test
    fun `when Get Story Should Not Null`() = runTest {
        val dummyStories = DataDummy.generateDummyStoryResponse()
        val expectedStories = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStories.value = PagedTest.snapshot(dummyStories)

        val actual = storyRepository.getStory("token").getOrAwaitValue()
        assertNotNull(actual)
    }

    @Test
    fun `when Get Login Should Not Null`() = runTest {
        val expectedLogin = DataDummy.loginResponseSuccess()
        val actual = storyRepository.loginUser(dummyEmail, dummyPassword)
        actual.observeForTesting {
            assertNotNull(actual)
            assertEquals(
                expectedLogin,
                (actual.value as ResourceData.Success).data
            )
        }
    }

    @Test
    fun `when Get Register Should Not Null`() = runTest {
        val expectedRegister = DataDummy.registerResponseSuccess()
        val actual = storyRepository.registerUser("Hendryansha", "hendy@gmail.com", "123456789")
        actual.observeForTesting {
            assertNotNull(actual)
            assertEquals(
                expectedRegister,
                (actual.value as ResourceData.Success).data
            )
        }
    }

    @Test
    fun `when Get Location Should Not Null`() = runTest {
        val expectedLocation = DataDummy.generateDummyMaps()
        val actual = storyRepository.getLocation("Bearer gfsdgu2345")
        actual.observeForTesting {
            assertNotNull(actual)
            assertEquals(
                expectedLocation,
                (actual.value as ResourceData.Success).data
            )
        }
    }

}