package com.example.storyapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.activity.AddStoryActivity
import com.example.storyapp.activity.LoginActivity
import com.example.storyapp.activity.MapsActivity
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.model.StoryModelFactory
import com.example.storyapp.model.ViewModelFactory
import com.example.storyapp.model.mainModel.MainViewModel
import com.example.storyapp.model.mainModel.StoryViewModel
import com.example.storyapp.preference.UserPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel: MainViewModel
    private val storyViewModel: StoryViewModel by viewModels{
        StoryModelFactory(this)
    }
    companion object{
        var TAG = "MAIN_ACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvStory.layoutManager =LinearLayoutManager(this@MainActivity)
        setupViewModel()
        setupAction()
    }

    private fun setupView(){
        mainViewModel.getUser().observe(this){user ->
            if(user.isLogin){
                val adapter = StoryAdapter()
                binding.rvStory.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
                storyViewModel.listStory(user.token).observe(this) {
                    adapter.submitData(lifecycle, it)
                }
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        setupView()
    }

    private fun setupAction(){
        binding.addStory.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
        binding.maps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectionMode: Int){
        if(selectionMode == R.id.logout){
            mainViewModel.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}