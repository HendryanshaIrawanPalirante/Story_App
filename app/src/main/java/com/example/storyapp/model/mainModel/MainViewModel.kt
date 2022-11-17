package com.example.storyapp.model.mainModel

import androidx.lifecycle.*
import com.example.storyapp.model.userModel.UserModel
import com.example.storyapp.preference.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference): ViewModel() {

    fun saveUser(user: UserModel){
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun getUser(): LiveData<UserModel>{
        return pref.getUser().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            pref.logout()
        }
    }

}