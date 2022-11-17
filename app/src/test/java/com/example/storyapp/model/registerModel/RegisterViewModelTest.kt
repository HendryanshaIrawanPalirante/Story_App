package com.example.storyapp.model.registerModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.DataDummy
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.response.RegisterResponse
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
class RegisterViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegister = DataDummy.registerResponseSuccess()
    private val dummyName = "Ian"
    private val dummyEmail = "hend@gmail.com"
    private val dummyPassword = "12345"

    @Before
    fun setup(){
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when Register Should Not Null and Return Success`(){
        val expectedResponse = MutableLiveData<ResourceData<RegisterResponse>>()
        expectedResponse.value = ResourceData.Success(dummyRegister)
        `when`(storyRepository.registerUser(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        val actual = registerViewModel.registerUser(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        Mockito.verify(storyRepository).registerUser(dummyName, dummyEmail, dummyPassword)
        assertNotNull(actual)
        assertTrue(actual is ResourceData.Success)
        assertEquals(dummyRegister, (actual as ResourceData.Success).data)
    }

}