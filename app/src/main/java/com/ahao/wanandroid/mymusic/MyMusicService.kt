package com.ahao.wanandroid.mymusic

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MyMusicService : Service() {

    val mediaPlayer = MediaPlayer()

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    class MusicBinder() : Binder() ,MusicOperator{
        override fun playMusic(path: String) {
            TODO("Not yet implemented")
        }

        override fun onPlayButtonClick() {
            TODO("Not yet implemented")
        }

        override fun getPlayState(): Int {
            TODO("Not yet implemented")
        }

        override fun playNextMusic() {
            TODO("Not yet implemented")
        }

        override fun getPlayList() {
            TODO("Not yet implemented")
        }

        override fun addPlayList() {
            TODO("Not yet implemented")
        }

    }

    interface MusicOperator {
        fun playMusic(path: String)
        fun onPlayButtonClick()
        fun getPlayState(): Int
        fun playNextMusic()
        fun getPlayList()
        fun addPlayList()
    }
}