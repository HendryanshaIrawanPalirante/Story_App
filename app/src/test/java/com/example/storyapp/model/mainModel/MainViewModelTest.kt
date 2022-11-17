package com.example.storyapp.model.mainModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.data.DataDummy
import com.example.storyapp.model.userModel.UserModel
import com.example.storyapp.preference.UserPreference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var userPreference: UserPreference
    private lateinit var mainViewModel: MainViewModel

    private val dummyUser = DataDummy.generateDummyUser()

    @Before
    fun setup(){
        mainViewModel = MainViewModel(userPreference)
//        mainViewModel.saveUser(dummyUser)
//        mainViewModel.logout()
    }

    @Test
    fun `when Methods saveUser Call Successfully`() = runTest{
        val dummyUser = DataDummy.generateDummyUser()
        userPreference.saveUser(dummyUser)
        Mockito.verify(userPreference).saveUser(dummyUser)
    }

    @Test
    fun `when getUser Should Not Null`(){
        val observer = Observer<UserModel> {}
        try {
            `when`(userPreference.getUser()).thenReturn(flowOf(dummyUser))
            val actualUser = mainViewModel.getUser().observeForever(observer)
            Assert.assertNotNull(actualUser)
        } finally {
            mainViewModel.getUser().removeObserver(observer)
        }
    }

    @Test
    fun `when Methods logout Call Successfully`() = runTest {
        userPreference.logout()
        Mockito.verify(userPreference).logout()
    }

}
