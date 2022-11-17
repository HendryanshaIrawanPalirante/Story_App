package com.example.storyapp.view

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText

class PasswordEditText: AppCompatEditText {

    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }

    private fun init(){

        addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                when{
                    p0.toString().isEmpty() ->{
                        error = "Password Tidak Boleh Kososng"
                    }
                    p0.toString().length < 6 ->{
                        error = "Password Harus Minimal 6 Karakter"
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = "Masukan Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

}