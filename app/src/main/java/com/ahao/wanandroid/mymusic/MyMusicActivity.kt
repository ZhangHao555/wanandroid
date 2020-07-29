package com.ahao.wanandroid.mymusic

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahao.wanandroid.R
import com.ahao.wanandroid.baseview.BaseActivity
import com.ahao.wanandroid.util.loge
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_my_music.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class MyMusicActivity : BaseActivity() {
    override fun getResLayoutId() = R.layout.activity_my_music

    private val musicList = mutableListOf<MusicItem>()

    private val searchRootList = mutableListOf<String>()

    private val musicSuffix = listOf("mp3", "wav", "flac", "ape", "m4a")

    private val adapter = Adapter()

    companion object {
        fun newIntent(context: Context?) = Intent(context, MyMusicActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initData()
        initDataSource()
    }

    private fun initData() {
        requestPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))

        music_list.layoutManager = LinearLayoutManager(this)
        music_list.adapter = adapter
    }

    private fun initDataSource() {
        if (Environment.isExternalStorageEmulated()) {
            searchRootList.add(Environment.getExternalStorageDirectory().absolutePath)
        }

        showProgressDialog()
        GlobalScope.launch(Dispatchers.IO)
        {
            getDiskMusic()
            withContext(Dispatchers.Main) {
                hideProgressDialog()
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun recursiveTraversal(it: String) {
        loge("traversal $it")
        val file = File(it)
        if (file.isDirectory) {
            val list = file.list()
            if (list != null && list.isNotEmpty()) {
                list.forEach {
                    recursiveTraversal(file.absolutePath + File.separatorChar + it)
                }
            }
        } else {
            val lastIndexOf = file.name.lastIndexOf(".")
            if (lastIndexOf != -1) {
                val fileType = file.name.substring(lastIndexOf + 1)
                if (musicSuffix.contains(fileType)) {
                    val musicItem = extractInfoFromPath(it, fileType)
                    if (musicItem != null) {
                        musicList.add(musicItem)
                    }
                }
            }

        }
    }

    private fun extractInfoFromPath(path: String, suffix: String): MusicItem? {
        val file = File(path)
        val name = path.substring(path.lastIndexOf(File.separatorChar) + 1)
        when (suffix) {
            "mp3" -> {
                return MusicItem(name, file.path)
            }
        }

        return null
    }


    private fun getDiskMusic() {
        musicList.clear()
        searchRootList.forEach {
            recursiveTraversal(it)
        }
    }

    data class MusicItem(val name: String, val path: String, val author: String? = null, val duration: Int? = null)

    inner class Adapter() : BaseQuickAdapter<MusicItem, BaseViewHolder>(R.layout.view_music_item, musicList) {
        override fun convert(helper: BaseViewHolder, item: MusicItem?) {
            helper.setText(R.id.music_name, item?.name)
        }

    }
}