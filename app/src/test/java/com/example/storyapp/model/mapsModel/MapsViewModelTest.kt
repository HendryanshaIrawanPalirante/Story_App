package com.example.storyapp.model.mapsModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.DataDummy
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.response.ResponseAllStory
import com.example.storyapp.utils.ResourceData
import com.example.storyapp.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyMaps = DataDummy.generateDummyMaps()
    private val dummyToken = "AADFfnsjkgn484"

    @Before
    fun setup(){
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when Get Maps Should Not Null and Return Succes`(){
        val expectedResponse = MutableLiveData<ResourceData<ResponseAllStory>>()
        expectedResponse.value = ResourceData.Success(dummyMaps)
        `when`(storyRepository.getLocation(dummyToken)).thenReturn(expectedResponse)

        val actual = mapsViewModel.getLocation(dummyToken).getOrAwaitValue()
        Mockito.verify(storyRepository).getLocation(dummyToken)
        assertNotNull(actual)
        assertTrue(actual is ResourceData.Success)
        assertEquals(dummyMaps, (actual as ResourceData.Success).data)
    }

}