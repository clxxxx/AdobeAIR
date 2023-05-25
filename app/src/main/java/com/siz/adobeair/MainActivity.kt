package com.siz.adobeair

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.siz.adobeair.model.User
import java.io.File


class MainActivity : AppCompatActivity() {

    private var videoPlayer : EmptyControlVideo? = null
    private var user : User? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()

        findViewById<Button>(R.id.open_new).setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivityForResult(intent, 100)
        }

//        val hadPermission: Boolean = PermissionUtils.hasSelfPermissions(this) { Manifest.permission.WRITE_EXTERNAL_STORAGE }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hadPermission) {
//            val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            requestPermissions(permissions, 1110)
//        }


//        val url = "/storage/emulated/0/OPT/video/影片1/卑鄙的我.lsy"
//        if(File("/storage/emulated/0/OPT/video/影片1/卑鄙的我.lsy").exists()){
//            Log.d("+++++++++++++", url)
//        }
//        videoPlayer = findViewById(R.id.video_top)
//        videoPlayer?.setUp(url, false, "")
//        videoPlayer?.startPlayLogic()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null){
                user = data.getParcelableExtra("user")
                Log.d("+++++++++", user!!.name!!)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}