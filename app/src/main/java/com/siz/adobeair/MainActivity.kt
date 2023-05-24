package com.siz.adobeair

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import java.io.File


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()

        findViewById<Button>(R.id.open_new).setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
        }

        val url = "/storage/sdcard0/OPT/009 保镖_高清.flv"
        Log.e("+++++++++",url+"")
        findViewById<EmptyControlVideo>(R.id.video_top).setUp(url, true, "")

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}