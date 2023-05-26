package com.siz.adobeair

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.permissionx.guolindev.PermissionX
import com.siz.adobeair.model.User
import java.io.File
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private var convergenceSet : Button? = null
    private var addConvergence : Button? = null
    private var reduceConvergence : Button? = null
    private var confirmConvergence : Button? = null
    private var addImg : Button? = null
    private var binocularVision : Button? = null
    private var chooseVideo : Button? = null
    private var outreachSet : Button? = null
    private var addOutreach : Button? = null
    private var reduceOutreach : Button? = null
    private var confirmOutreach : Button? = null
    private var swapping : Button? = null
    private var query : Button? = null
    private var convergence : Button? = null
    private var outreach : Button? = null
    private var flexible : Button? = null
    private var limit : Button? = null
    private var accelerate : Button? = null
    private var continued : Button? = null
    private var jijilingji : Button? = null
    private var waiwaiwailing : Button? = null
    private var waiwailingji : Button? = null
    private var huihuilingji : Button? = null
    private var moderate : Button? = null
    private var end : Button? = null
    private var setValue : TextView? = null
    private var videoPlayer : EmptyControlVideo? = null
    private var user : User? =null

    private var speed : Long = 6000
    private var topAnimation: TranslateAnimation? =null
    private var botAnimation: TranslateAnimation? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()

        findViewById<Button>(R.id.open_new).setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivityForResult(intent, 100)
        }

        setValue = findViewById(R.id.set_value)
        convergenceSet = findViewById(R.id.convergence_set)
        convergenceSet?.setOnClickListener {
            convergenceSet?.isEnabled = false
            addConvergence?.isEnabled = true
            reduceConvergence?.isEnabled = true
            confirmConvergence?.isEnabled = true
        }
        addConvergence = findViewById(R.id.add_convergence)
        addConvergence?.setOnClickListener {
            setValue(0)
        }
        reduceConvergence = findViewById(R.id.reduce_convergence)
        reduceConvergence?.setOnClickListener {
            setValue(0)
        }
        confirmConvergence = findViewById(R.id.confirm_convergence)
        confirmConvergence?.setOnClickListener {
            addConvergence?.isEnabled = false
            reduceConvergence?.isEnabled = false
            confirmConvergence?.isEnabled = false
            outreachSet?.isEnabled = true

        }
        addImg = findViewById(R.id.add_img)
        addImg?.setOnClickListener {

        }
        binocularVision = findViewById(R.id.binocular_vision)
        binocularVision?.setOnClickListener {

        }
        chooseVideo = findViewById(R.id.choose_video)
        chooseVideo?.setOnClickListener {

        }
        outreachSet = findViewById(R.id.outreach_set)
        outreachSet?.setOnClickListener {
            addOutreach?.isEnabled = true
            reduceOutreach?.isEnabled = true
            confirmOutreach?.isEnabled = true

        }
        addOutreach = findViewById(R.id.add_outreach)
        addOutreach?.setOnClickListener {
            setValue(0)
        }
        reduceOutreach = findViewById(R.id.reduce_outreach)
        reduceOutreach?.setOnClickListener {
            setValue(0)
        }
        confirmOutreach = findViewById(R.id.confirm_outreach)
        confirmOutreach?.setOnClickListener {

        }
        swapping = findViewById(R.id.swapping)
        query = findViewById(R.id.query)
        convergence = findViewById(R.id.convergence)
        outreach = findViewById(R.id.outreach)
        flexible = findViewById(R.id.flexible)
        limit = findViewById(R.id.limit)
        accelerate = findViewById(R.id.accelerate)
        accelerate?.setOnClickListener {
            speed -= 200
            topAnimation?.duration = speed
            Log.e("++++++++++",speed.toString())
        }
        continued = findViewById(R.id.continued)
        jijilingji = findViewById(R.id.jijilingji)
        waiwaiwailing = findViewById(R.id.waiwaiwailing)
        waiwailingji = findViewById(R.id.waiwailingji)
        huihuilingji = findViewById(R.id.huihuilingji)
        moderate = findViewById(R.id.moderate)
        moderate?.setOnClickListener {
            speed += 200
            topAnimation?.duration = speed
        }
        end = findViewById(R.id.end)

        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .request { allGranted, _, _ ->
                if (!allGranted) {
                    exitForce()
                }
            }

        val url = "/storage/emulated/legacy/OPT/009 保镖_高清.flv"
        if(File("/storage/emulated/legacy/OPT/009 保镖_高清.flv").exists()){
            Log.d("+++++++++++++", url)
        }
        videoPlayer = findViewById(R.id.video_top)
        videoPlayer?.setUp(url, false, "")
        videoPlayer?.startPlayLogic()

        topAnimation = TranslateAnimation(0f,-200f,0f,0f)
        topAnimation?.duration = speed
        topAnimation?.repeatCount = Animation.INFINITE
        topAnimation?.repeatMode = Animation.REVERSE
        videoPlayer?.startAnimation(topAnimation)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null){
                user = data.getParcelableExtra("user")
                initOperateButton()
            }
        }
    }

    private fun initOperateButton(){
        if(user == null) return
        chooseVideo?.isEnabled = true
        query?.isEnabled = true
        if (!TextUtils.isEmpty(user?.videoName)){
            convergenceSet?.isEnabled = true
            convergence?.isEnabled = true
            outreach?.isEnabled = true
            flexible?.isEnabled = true
            limit?.isEnabled = true
            jijilingji?.isEnabled = true
            waiwaiwailing?.isEnabled = true
            waiwailingji?.isEnabled = true
            huihuilingji?.isEnabled = true
        }
        accelerate?.isEnabled = true
        moderate?.isEnabled = true
    }

    private fun setValue(value : Int){
        val convergence = user?.setValues?.get(0)!!.convergence
        val outreach = user?.setValues?.get(0)!!.outreach
        if (addConvergence!!.isEnabled){
            setValue?.text = "前会聚值 : $convergence    现会聚值 : $value"
        }
        if (addOutreach!!.isEnabled){
            setValue?.text = "前会聚值 : $outreach    现会聚值 : $value"
        }
    }

    private fun exitForce() {
        finish()
        exitProcess(0)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}