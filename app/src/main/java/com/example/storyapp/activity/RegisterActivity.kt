package com.example.storyapp.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapp.databinding.ActivityRegisterBinding
import com.example.storyapp.model.StoryModelFactory
import com.example.storyapp.model.registerModel.RegisterViewModel
import com.example.storyapp.remote.response.RegisterResponse
import com.example.storyapp.utils.ResourceData
import com.google.gson.Gson
import retrofit2.HttpException

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels{
        StoryModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setUpAction()
        playAnimation()

    }

    private fun setupView(){
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

    private fun setUpAction(){
        binding.btnRegister.setOnClickListener{
            register()
        }
    }

    private fun register(){
        val name = binding.edRegisterName.text.toString().trim()
        val email = binding.edRegisterEmail.text.toString().trim()
        val password = binding.edRegisterPassword.text.toString().trim()
        registerViewModel.registerUser(name, email, password).observe(this) { it ->
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
                                this,
                                data.message ?: "Register Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    is ResourceData.Error ->{
                        showLoad(false)
                        var message = "Register Gagal"
                        try{
                            Gson().fromJson(
                                (it.message as HttpException).response()?.errorBody()?.string(), RegisterResponse::class.java
                            ).message?.let {
                                message = it
                            }
                        }catch (e: Exception){}
                        Toast.makeText(
                            this@RegisterActivity,
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

        val title = ObjectAnimator.ofFloat(binding.titleTextRegister, View.ALPHA, 1f).setDuration(500)
        val editNameLayout = ObjectAnimator.ofFloat(binding.editName, View.ALPHA, 1f).setDuration(500)
        val editEmailLayout = ObjectAnimator.ofFloat(binding.editEmail, View.ALPHA, 1f).setDuration(500)
        val editPasswordLayout = ObjectAnimator.ofFloat(binding.editPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, editNameLayout, editEmailLayout, editPasswordLayout, btnRegister)
            startDelay = 500
        }.start()

    }

}