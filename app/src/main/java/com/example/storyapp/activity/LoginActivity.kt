package com.example.storyapp.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.MainActivity
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.model.StoryModelFactory
import com.example.storyapp.model.ViewModelFactory
import com.example.storyapp.model.loginModel.LoginViewModel
import com.example.storyapp.model.mainModel.MainViewModel
import com.example.storyapp.model.userModel.UserModel
import com.example.storyapp.preference.UserPreference
import com.example.storyapp.remote.response.LoginResponse
import com.example.storyapp.utils.ResourceData
import com.google.gson.Gson
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels{
        StoryModelFactory(this)
    }
    private lateinit var mainViewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
    }

    private fun setupAction(){
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

    }

    private fun login(){
        val email = binding.edLoginEmail.text.toString().trim()
        val password = binding.edLoginPassword.text.toString().trim()
        loginViewModel.loginUser(email, password).observe(this){ it ->
            if(it != null){
                when(it){
                    is ResourceData.Loading ->{
                        showLoad(true)
                    }
                    is ResourceData.Success ->{
                        showLoad(false)
                        val data = it.data
                        if(data?.error == true){
                            Toast.makeText(
                                this@LoginActivity,
                                data.message ?: "Gagal Login",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            AlertDialog.Builder(this).apply {
                                setTitle("Berhasil Login")
                                setMessage("Selamat Datatang ${data?.loginResult?.name}")
                                setPositiveButton("Ok!"){_, _ ->
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                }
                                create()
                                show()
                                val userId = data?.loginResult?.userId
                                val name = data?.loginResult?.name
                                val token = data?.loginResult?.token

                                userId?.let { it1 -> name?.let { it2 ->
                                    token?.let { it3 ->
                                        UserModel(it1,
                                            it2, it3, true)
                                    }
                                } }
                                    ?.let { it2 -> mainViewModel.saveUser(it2) }

                            }
                        }
                    }
                    is ResourceData.Error ->{
                        showLoad(false)
                        var message = "Anda Gagal Login"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.toString(),LoginResponse::class.java
                            ).message?.let {
                                message = it
                            }
                        }catch (e: Exception){}
                        Toast.makeText(
                            this@LoginActivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoad(isLoading: Boolean) {
        if (isLoading){
            binding.progressbar.visibility = View.VISIBLE
        }
        else {
            binding.progressbar.visibility = View.GONE
        }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextLogin, View.ALPHA, 1f).setDuration(500)
        val editEmailLayout = ObjectAnimator.ofFloat(binding.editEmail, View.ALPHA, 1f).setDuration(500)
        val editPasswordLayout = ObjectAnimator.ofFloat(binding.editPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(btnLogin, btnRegister)
        }

        AnimatorSet().apply {
            playSequentially(title, editEmailLayout, editPasswordLayout, together)
            startDelay = 500
        }.start()

    }

}