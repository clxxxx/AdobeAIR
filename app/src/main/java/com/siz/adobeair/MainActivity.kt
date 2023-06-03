package com.siz.adobeair

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.permissionx.guolindev.PermissionX
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import com.siz.adobeair.model.SetValue
import com.siz.adobeair.model.User
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.io.File
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    companion object {
        val PATH = Environment.getExternalStorageDirectory().absolutePath + "/OPT"
    }

    private var convergenceSet: Button? = null
    private var addConvergence: Button? = null
    private var reduceConvergence: Button? = null
    private var confirmConvergence: Button? = null
    private var addImg: Button? = null
    private var binocularVision: Button? = null
    private var chooseVideo: Button? = null
    private var outreachSet: Button? = null
    private var addOutreach: Button? = null
    private var reduceOutreach: Button? = null
    private var confirmOutreach: Button? = null
    private var swapping: Button? = null
    private var query: Button? = null
    private var convergence: Button? = null
    private var outreach: Button? = null
    private var flexible: Button? = null
    private var limit: Button? = null
    private var accelerate: Button? = null
    private var continued: Button? = null
    private var jijilingji: Button? = null
    private var waiwaiwailing: Button? = null
    private var waiwailingji: Button? = null
    private var huihuilingji: Button? = null
    private var moderate: Button? = null
    private var end: Button? = null

    private var setValue: TextView? = null
    private var videoPlayerTop: EmptyControlVideo? = null
    private var videoPlayerBot: EmptyControlVideo? = null

    private var videoPath : String? = null
    private var mSetValue : SetValue? = null
    private var user: User? = null

    private var convergenceValue: Int = 0
    private var outreachValue: Int = 0
    private var speed: Long = 5000
    private var topAnimation: TranslateAnimation? = null
    private var botAnimation: TranslateAnimation? = null

    private lateinit var realm: Realm

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
            setValue?.visibility = View.VISIBLE
            setValue()
            videoPlayerTop?.setImgSrc(getBitmap("/systemImage/huiju.jpg"))
            videoPlayerBot?.setImgSrc(getBitmap("/systemImage/huiju.jpg"))
        }
        addConvergence = findViewById(R.id.add_convergence)
        addConvergence?.setOnClickListener {
            convergenceValue += 10
            setValue()
            setlocation()
        }
        reduceConvergence = findViewById(R.id.reduce_convergence)
        reduceConvergence?.setOnClickListener {
            if (convergenceValue == 0) return@setOnClickListener
            convergenceValue -= 5
            setValue()
            setlocation()
        }
        confirmConvergence = findViewById(R.id.confirm_convergence)
        confirmConvergence?.setOnClickListener {
            addConvergence?.isEnabled = false
            reduceConvergence?.isEnabled = false
            confirmConvergence?.isEnabled = false
            outreachSet?.isEnabled = true
            setValue?.text = ""
            setlocation()
        }
        addImg = findViewById(R.id.add_img)
        addImg?.setOnClickListener {

        }
        binocularVision = findViewById(R.id.binocular_vision)
        binocularVision?.setOnClickListener {

        }
        chooseVideo = findViewById(R.id.choose_video)
        chooseVideo?.setOnClickListener {
            val intent = Intent(this, ChooseVideoActivity::class.java)
            startActivityForResult(intent, 200)
        }
        outreachSet = findViewById(R.id.outreach_set)
        outreachSet?.setOnClickListener {
            outreachSet?.isEnabled = false
            addOutreach?.isEnabled = true
            reduceOutreach?.isEnabled = true
            confirmOutreach?.isEnabled = true
            setValue()
            setlocation()
         }
        addOutreach = findViewById(R.id.add_outreach)
        addOutreach?.setOnClickListener {
            outreachValue += 8
            setValue()
            setlocation()
        }
        reduceOutreach = findViewById(R.id.reduce_outreach)
        reduceOutreach?.setOnClickListener {
            if (outreachValue == 0) return@setOnClickListener
            outreachValue -= 4
            setValue()
            setlocation()
        }
        confirmOutreach = findViewById(R.id.confirm_outreach)
        confirmOutreach?.setOnClickListener {
            //保存设置值
            realm.executeTransaction {
                val u = realm.where<User>().equalTo("id", user?.id).findFirst()
                val value = realm.createObject<SetValue>()
                if (mSetValue != null){
                    if (convergenceValue == 0){
                        convergenceValue = mSetValue?.convergence!!
                    }
                    if (outreachValue == 0){
                        outreachValue = mSetValue?.outreach!!
                    }
                }
                value.convergence = convergenceValue
                value.outreach = outreachValue
                value.setTime = System.currentTimeMillis()
                u!!.setValues.add(value)
            }
            addOutreach?.isEnabled = false
            reduceOutreach?.isEnabled = false
            confirmOutreach?.isEnabled = false
            setValue?.visibility = View.INVISIBLE
            playVideoUiState()
        }
        swapping = findViewById(R.id.swapping)
        query = findViewById(R.id.query)
        query?.setOnClickListener {
            val intent = Intent(this, ValueRecordActivity::class.java)
            intent.putExtra("userId", user?.id)
            startActivity(intent)
        }
        convergence = findViewById(R.id.convergence)
        convergence?.setOnClickListener {
            if (convergence!!.isSelected) return@setOnClickListener
            restoreButtonState()
            convergence?.isSelected = true
            videoPlayerTop?.setInVisibleImg()
            videoPlayerBot?.setInVisibleImg()
            videoPlayerTop?.setUp(videoPath, false, "")
            videoPlayerBot?.setUp(videoPath, false, "")
            videoPlayerTop?.startPlayLogic()
//            videoPlayerBot?.startPlayLogic()
//            startAnimation(-100f,100f, 15)
        }
        outreach = findViewById(R.id.outreach)
        outreach?.setOnClickListener {
            if (outreach!!.isSelected) return@setOnClickListener
            restoreButtonState()
            outreach?.isSelected = true
            startAnimation(100f, -100f, 15)
        }
        flexible = findViewById(R.id.flexible)
        flexible?.setOnClickListener {
            if (outreach!!.isSelected) return@setOnClickListener
            restoreButtonState()
            outreach?.isSelected = true
            startAnimation(100f, -100f, 15)
        }
        limit = findViewById(R.id.limit)
        limit?.setOnClickListener {
            if (limit!!.isSelected) return@setOnClickListener
            restoreButtonState()
            limit?.isSelected = true
            videoPlayerTop?.clearAnimation()
            videoPlayerBot?.clearAnimation()
            topAnimation = TranslateAnimation(0f, -100f, 0f, 0f)
            topAnimation?.duration = speed / 2
            topAnimation?.fillAfter = true
            topAnimation?.isFillEnabled = true
            topAnimation?.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    topAnimation = TranslateAnimation(-100f, 100f, 0f, 0f)
                    topAnimation?.duration = speed
                    topAnimation?.repeatCount = 15
                    topAnimation?.repeatMode = Animation.REVERSE
                    videoPlayerTop?.startAnimation(topAnimation)
                    botAnimation = TranslateAnimation(100f, -100f, 0f, 0f)
                    botAnimation?.duration = speed
                    botAnimation?.repeatCount = 15
                    botAnimation?.repeatMode = Animation.REVERSE
                    videoPlayerBot?.startAnimation(botAnimation)
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }

            })
            videoPlayerTop?.startAnimation(topAnimation)
            botAnimation = TranslateAnimation(0f, 100f, 0f, 0f)
            botAnimation?.duration = speed / 2
            topAnimation?.fillAfter = true
            topAnimation?.isFillEnabled = true
            videoPlayerBot?.startAnimation(botAnimation)
        }
        accelerate = findViewById(R.id.accelerate)
        accelerate?.setOnClickListener {
            if (speed > 500) {
                speed -= 500
                topAnimation?.restrictDuration(speed)
                botAnimation?.restrictDuration(speed)
            }
        }
        continued = findViewById(R.id.continued)
        continued?.setOnClickListener {
        }
        jijilingji = findViewById(R.id.jijilingji)
        jijilingji?.setOnClickListener {
        }
        waiwaiwailing = findViewById(R.id.waiwaiwailing)
        waiwaiwailing?.setOnClickListener {
        }
        waiwailingji = findViewById(R.id.waiwailingji)
        waiwailingji?.setOnClickListener {
        }
        huihuilingji = findViewById(R.id.huihuilingji)
        huihuilingji?.setOnClickListener {
        }
        moderate = findViewById(R.id.moderate)
        moderate?.setOnClickListener {
            speed += 500
            topAnimation?.restrictDuration(speed)
            botAnimation?.restrictDuration(speed)
        }
        end = findViewById(R.id.end)
        end?.setOnClickListener {
        }
        PermissionX.init(this)
            .permissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .request { allGranted, _, _ ->
                if (!allGranted) {
                    exitForce()
                } else {
                    val opt = File(PATH)
                    if (!opt.exists()) {
                        opt.mkdir()
                        val sys = File(opt.absolutePath, "sys")
                        sys.mkdir()
                        val systemImage = File(opt.absolutePath, "systemImage")
                        systemImage.mkdir()
                        val video = File(opt.absolutePath, "video")
                        video.mkdir()
                        val videoImage = File(opt.absolutePath, "videoImage")
                        videoImage.mkdir()
                    }
                }
            }

        videoPlayerTop = findViewById(R.id.video_top)
        videoPlayerBot = findViewById(R.id.video_bot)
        Log.e("+++++++++++","000000000")
        videoPlayerTop?.playTag = "videoPlayerTop"
        videoPlayerTop?.playPosition = 1
        videoPlayerBot?.playTag = "videoPlayerBot"
        videoPlayerBot?.playPosition = 2
        realm = Realm.getDefaultInstance()
        videoPlayerTop?.setGSYVideoProgressListener { _, _, currentPosition, _ ->
            Log.e("currentPosition", currentPosition.toString())
        }


        videoPlayerTop?.setGSYStateUiListener { state ->
            when(state){
                GSYVideoView.CURRENT_STATE_PLAYING -> {
                    //开始播放
                    //            topAnimation = TranslateAnimation(0f, -200f, 0f, 0f)
//            topAnimation?.duration = speed
//            topAnimation?.repeatCount = Animation.INFINITE
//            topAnimation?.repeatMode = Animation.REVERSE
//            videoPlayerTop?.startAnimation(topAnimation)
                }
                GSYVideoView.CURRENT_STATE_PAUSE -> {
                    //播放暂停

                }
                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> {
                    //播放结束

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                100 -> {
                    if (data != null) {
                        val id = data.getLongExtra("id", 0)
                        user = realm.where<User>().equalTo("id", id).findFirst()
                        videoPath = user?.videoName
                        initOperateButton()
                    }
                }
                200 -> {
                    if (data != null) {
                        val needUpdateUiState = TextUtils.isEmpty(videoPath)
                        videoPath = data.getStringExtra("path")
                        if (needUpdateUiState) initOperateButton()
                    }
                }
            }
        }
    }

    private fun initOperateButton() {
        if (user == null) return
        chooseVideo?.isEnabled = true
        query?.isEnabled = true
        if ((user?.setValues != null && user?.setValues!!.size > 0 && !TextUtils.isEmpty(videoPath)) || user?.videoProgress!! > 0){
            mSetValue = user?.setValues!![0]
            playVideoUiState()
        }
        if (!TextUtils.isEmpty(videoPath)) {
            convergenceSet?.isEnabled = true
        }
    }

    private fun playVideoUiState(){
        convergence?.isEnabled = true
        outreach?.isEnabled = true
        flexible?.isEnabled = true
        limit?.isEnabled = true
        jijilingji?.isEnabled = true
        waiwaiwailing?.isEnabled = true
        waiwailingji?.isEnabled = true
        huihuilingji?.isEnabled = true
        videoPlayerTop?.setImgSrc(getBitmap("/systemImage/kaishi.jpg"))
        videoPlayerBot?.setImgSrc(getBitmap("/systemImage/kaishi.jpg"))
    }

    private fun startPlay(){
        convergenceSet?.isEnabled = false

    }

    private fun setValue() {
        var convergence = 0
        if (user?.setValues != null && user?.setValues!!.size > 0){
            convergence = user?.setValues?.get(0)!!.convergence
        }
        var outreach = 0
        if (user?.setValues != null && user?.setValues!!.size > 0){
            outreach = user?.setValues?.get(0)!!.outreach
        }
        if (addConvergence!!.isEnabled) {
            setValue?.text = "前会聚值 : $convergence    现会聚值 : $convergenceValue"
        }
        if (addOutreach!!.isEnabled) {
            setValue?.text = "前外展值 : $outreach    现外展值 : $outreachValue"
        }
    }

    private fun setlocation(){
        if (addConvergence!!.isEnabled) {
            val layoutParamsTop =  videoPlayerTop?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsTop.marginEnd = dip2px(convergenceValue / 2 * 3.8f)
            videoPlayerTop?.layoutParams = layoutParamsTop
            val layoutParamsBot =  videoPlayerBot?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsBot.marginStart = dip2px(convergenceValue / 2 * 3.8f)
            videoPlayerBot?.layoutParams = layoutParamsBot
        }
        if (addOutreach!!.isEnabled) {
            val layoutParamsTop =  videoPlayerTop?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsTop.marginEnd = 0
            layoutParamsTop.marginStart = dip2px(outreachValue / 2 * 3.8f)
            videoPlayerTop?.layoutParams = layoutParamsTop
            val layoutParamsBot =  videoPlayerBot?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsBot.marginStart = 0
            layoutParamsBot.marginEnd = dip2px(outreachValue / 2 * 3.8f)
            videoPlayerBot?.layoutParams = layoutParamsBot
        }
    }

    private fun restoreButtonState(){
        convergence?.isSelected = false
        outreach?.isSelected = false
        flexible?.isSelected = false
        limit?.isSelected = false
        jijilingji?.isSelected = false
        waiwaiwailing?.isSelected = false
        waiwailingji?.isSelected = false
        huihuilingji?.isSelected = false
        accelerate?.isSelected = false
        moderate?.isSelected = false
    }

    private fun startAnimation(topX: Float, botX: Float, repeatCount: Int){
        videoPlayerTop?.clearAnimation()
        videoPlayerBot?.clearAnimation()
        topAnimation = TranslateAnimation(0f, topX, 0f, 0f)
        topAnimation?.duration = speed
        topAnimation?.repeatCount = repeatCount
        topAnimation?.repeatMode = Animation.REVERSE
        videoPlayerTop?.startAnimation(topAnimation)
        botAnimation = TranslateAnimation(0f, botX, 0f, 0f)
        botAnimation?.duration = speed
        botAnimation?.repeatCount = repeatCount
        botAnimation?.repeatMode = Animation.REVERSE
        videoPlayerBot?.startAnimation(botAnimation)
    }

    private fun getBitmap(name: String): Bitmap? {
        return BitmapFactory.decodeFile(PATH + name)
    }

    private fun dip2px(dipValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    override fun onResume() {
        super.onResume()
        CustomManager.onResumeAll()
    }

    override fun onPause() {
        super.onPause()
        CustomManager.onPauseAll()
    }

    private fun exitForce() {
        finish()
        exitProcess(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        CustomManager.clearAllVideo()
    }
}