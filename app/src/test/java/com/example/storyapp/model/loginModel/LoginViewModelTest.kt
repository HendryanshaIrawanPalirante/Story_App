package com.example.storyapp.model.loginModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.data.DataDummy
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.remote.response.LoginResponse
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
class LoginViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLogin = DataDummy.loginResponseSuccess()
    private val dummyEmail = "hend@gmail.com"
    private val dummyPassword = "123456789"

    @Before
    fun setup(){
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when Login Should Not Null And Return Success`(){
        val expectedResponse = MutableLiveData<ResourceData<LoginResponse?>>()
        expectedResponse.value = ResourceData.Success(dummyLogin)
        `when`(storyRepository.loginUser(dummyEmail, dummyPassword)).thenReturn(expectedResponse)

        val actual = loginViewModel.loginUser(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(storyRepository).loginUser(dummyEmail, dummyPassword)
        assertNotNull(actual)
        assertTrue(actual is ResourceData.Success)
        assertEquals(dummyLogin, (actual as ResourceData.Success).data)
    }

}
