package com.ahao.wanandroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       findViewById<Button>(R.id.button).setOnClickListener {
           Intent(this@MainActivity,LoginActivity::class.java).run {
               startActivity(this)
           }
       }
    }



    companion object {
        fun newIntent(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
