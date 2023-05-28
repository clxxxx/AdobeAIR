package com.siz.adobeair

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import java.io.File

class ChooseVideoActivity : AppCompatActivity() {

    private lateinit var videoGroup : RecyclerView
    private lateinit var videos : RecyclerView
    private lateinit var videoGroupAdapter: VideoAdapter
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_video)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()

        videoGroup = findViewById(R.id.video_group)
        videoGroup.layoutManager = LinearLayoutManager(this)
        videoGroupAdapter = VideoAdapter()
        videoGroupAdapter.bindToRecyclerView(videoGroup)
        videos = findViewById(R.id.videoList)
        videos.layoutManager = LinearLayoutManager(this)
        videoAdapter = VideoAdapter()
        videoAdapter.bindToRecyclerView(videos)
        val groups = getAllDataFileName("video")
        groups.add(0, "默认")
        videoGroupAdapter.selectedGroup = 0
        videoGroupAdapter.setNewData(groups)
        videoGroupAdapter.setOnItemClickListener { _, _, position ->
            videoGroupAdapter.selectedGroup = position
            videoAdapter.setNewData(getAllDataFileName("video/"+videoGroupAdapter.data[position]))
            videoGroupAdapter.notifyDataSetChanged()
        }
        videoAdapter.setNewData(getAllDataFileName("video/"+videoGroupAdapter.data[0]))
        videoAdapter.setOnItemClickListener { _, _, position ->
            intent.putExtra("path", MainActivity.PATH+"/video/"+videoGroupAdapter.data[videoGroupAdapter.selectedGroup]
                    +"/"+ videoAdapter.data[position])
            setResult(RESULT_OK, intent)
            finish()
        }
        findViewById<Button>(R.id.close).setOnClickListener {
            finish()
        }
    }

    private fun getAllDataFileName(name : String) : MutableList<String> {
        val fileList = mutableListOf<String>()
        val file = File(MainActivity.PATH+"/"+name)
        val tempList = file.listFiles()
        if (tempList != null){
            for (f in tempList) {
                fileList.add(f.name)
            }
        }
        return fileList
    }
}