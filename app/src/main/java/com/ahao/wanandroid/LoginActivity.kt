package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)
    }

    companion object {
        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity.javaClass)
            context.startActivity(intent)
        }
    }
}