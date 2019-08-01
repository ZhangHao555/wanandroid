package com.ahao.wanandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.e("ss","dddddd1111111")
        initEvent()
    }

    private fun initEvent() {
        login.run {
            setOnClickListener{

            }
        }
    }

    companion object{
        fun actionStart(context: Context){
            Intent(context,LoginActivity::class.java).run {
                context.startActivity(this)
            }
        }
    }

}