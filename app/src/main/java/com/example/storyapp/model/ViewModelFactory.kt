package com.example.storyapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.model.mainModel.MainViewModel
import com.example.storyapp.preference.UserPreference

class ViewModelFactory(private val pref: UserPreference):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MainViewModel::class.java)->{
                MainViewModel(pref) as T
            }
            else ->throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}