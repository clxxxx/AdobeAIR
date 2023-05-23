package com.siz.adobeair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
import io.realm.Realm

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()

        findViewById<Button>(R.id.open_new).setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}