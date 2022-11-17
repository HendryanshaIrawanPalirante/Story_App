package com.example.storyapp.model.mainModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.PagedTest
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.data.DataDummy
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.response.ListStoryItem
import com.example.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var storyViewModel: StoryViewModel

    @Before
    fun setup(){
        storyViewModel = StoryViewModel(storyRepository)
    }

    @Test
    fun `when Get Story Should Not Null And Return Success`() = runTest{
        val dummyStory = DataDummy.generateDummyListStory()
        val dummyToken = "123445jfnsgjngf"
        val expectedResponse = MutableLiveData<PagingData<ListStoryItem>>()
        expectedResponse.value = PagedTest.snapshot(dummyStory)

        val noopListUpdateCallback = object: ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }

        Mockito.`when`(storyRepository.getStory(dummyToken)).thenReturn(expectedResponse)
        val actual = storyViewModel.listStory(dummyToken).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actual)

        Mockito.verify(storyRepository).getStory(dummyToken)
        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
    }

}