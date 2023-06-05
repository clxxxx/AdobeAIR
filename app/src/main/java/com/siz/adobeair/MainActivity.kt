package com.siz.adobeair

import android.Manifest
import android.animation.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.permissionx.guolindev.PermissionX
import com.siz.adobeair.model.SetValue
import com.siz.adobeair.model.User
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import xyz.doikki.videoplayer.player.VideoView
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
    private var videoPlayerTop: VideoControlView? = null
    private var videoPlayerBot: VideoControlView? = null

    private var videoPath : String? = null
    private var mSetValue : SetValue? = null
    private var user: User? = null

    private var convergenceValue: Int = 0
    private var outreachValue: Int = 0
    private var speed: Long = 5000
    private lateinit var topAnimation: ObjectAnimator
    private lateinit var botAnimation: ObjectAnimator

    private var img1Position = 0
    private var img2Position = 0
    private var imgListPath1 = mutableListOf<String>()
    private var imgListPath2 = mutableListOf<String>()

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
            playVideoUiState(false)
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
            if (!swapping!!.isEnabled){
                img1Position = 0
                swapping?.isEnabled = true
                videoPlayerTop?.setImgSrc(getBitmap(imgListPath1[img1Position]))
                videoPlayerBot?.setImgSrc(getBitmap(imgListPath1[img1Position]))
                addImg?.text = "去图"
            } else {
                swapping?.isEnabled = false
                videoPlayerTop?.setInVisibleImg()
                videoPlayerBot?.setInVisibleImg()
                addImg?.text = "加图"
            }
        }
        binocularVision = findViewById(R.id.binocular_vision)
        binocularVision?.setOnClickListener {
            videoPlayerTop?.setImgSrc(getBitmap(imgListPath2[img2Position]))
            videoPlayerBot?.setImgSrc(getBitmap(imgListPath2[img2Position + 1]))
            img2Position += 2
            if (img2Position == imgListPath2.size) img2Position = 0
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
                u!!.videoName = videoPath
                u.setValues.add(value)
            }
            addOutreach?.isEnabled = false
            reduceOutreach?.isEnabled = false
            confirmOutreach?.isEnabled = false
            setValue?.visibility = View.INVISIBLE
            playVideoUiState(true)
        }
        swapping = findViewById(R.id.swapping)
        swapping?.setOnClickListener {
            img1Position += 1
            if (img1Position == imgListPath1.size) img1Position = 0
            videoPlayerTop?.setImgSrc(getBitmap(imgListPath1[img1Position]))
            videoPlayerBot?.setImgSrc(getBitmap(imgListPath1[img1Position]))
        }
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
            startConvergence()
        }
        outreach = findViewById(R.id.outreach)
        outreach?.setOnClickListener {
            if (outreach!!.isSelected) return@setOnClickListener
            restoreButtonState()
            outreach?.isSelected = true
            startOutreach()
        }
        flexible = findViewById(R.id.flexible)
        flexible?.setOnClickListener {
            if (flexible!!.isSelected) return@setOnClickListener
            restoreButtonState()
            flexible?.isSelected = true
            startFlexible()
        }
        limit = findViewById(R.id.limit)
        limit?.setOnClickListener {
            if (limit!!.isSelected) return@setOnClickListener
            restoreButtonState()
            limit?.isSelected = true
            startLimit()
        }
        accelerate = findViewById(R.id.accelerate)
        accelerate?.setOnClickListener {
            if (speed > 500) {
                speed -= 500
                topAnimation.duration = speed
                botAnimation.duration = speed
            }
        }
        continued = findViewById(R.id.continued)
        continued?.setOnClickListener {
            if (videoPlayerTop?.player!!.isPlaying){
                videoPlayerTop?.player!!.pause()
                videoPlayerBot?.player!!.pause()
                topAnimation.pause()
                botAnimation.pause()
                continued?.text = "继续"
                accelerate?.isEnabled = false
                moderate?.isEnabled = false
            } else {
                videoPlayerTop?.player!!.resume()
                videoPlayerBot?.player!!.resume()
                topAnimation.resume()
                botAnimation.resume()
                continued?.text = "暂停"
                accelerate?.isEnabled = true
                moderate?.isEnabled = true
            }
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
            topAnimation.duration = speed
            botAnimation.duration = speed
        }
        end = findViewById(R.id.end)
        end?.setOnClickListener {
            topAnimation.end()
            botAnimation.end()
            playVideoUiState(false)
            videoPlayerTop?.setImgSrc(getBitmap("/systemImage/jieshu.jpg"))
            videoPlayerBot?.setImgSrc(getBitmap("/systemImage/jieshu.jpg"))
            binocularVision?.isEnabled = true
            chooseVideo?.isEnabled = false
            addImg?.isEnabled = false
            swapping?.isEnabled = false
            query?.isEnabled = false
            accelerate?.isEnabled = false
            moderate?.isEnabled = false
            continued?.isEnabled = false
            end?.isEnabled = false
            realm.executeTransaction {
                val u = realm.where<User>().equalTo("id", user?.id).findFirst()
                u!!.videoProgress = videoPlayerTop?.player!!.currentPosition
            }
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
                    } else {
                        getAllImgPath()
                    }
                }
            }

        videoPlayerTop = findViewById(R.id.video_top)
        videoPlayerBot = findViewById(R.id.video_bot)
        initAnimation()
        realm = Realm.getDefaultInstance()
        videoPlayerTop?.player!!.addOnStateChangeListener(object : VideoView.OnStateChangeListener {
            override fun onPlayerStateChanged(playerState: Int) {

            }

            override fun onPlayStateChanged(playState: Int) {
                if (playState == VideoView.STATE_PREPARED) {
                    val ratio =
                        videoPlayerTop?.player!!.videoSize[0] / videoPlayerTop?.player!!.videoSize[1]
                    if (ratio >= 3) {
                        val topParams = videoPlayerTop?.player!!.layoutParams
                        topParams!!.width = dip2px(380f)
                        videoPlayerTop?.player!!.layoutParams = topParams
                        val botParams = videoPlayerTop?.player!!.layoutParams
                        botParams!!.width = dip2px(380f)
                        videoPlayerBot?.player!!.layoutParams = botParams
                    } else {
                        val topParams = videoPlayerTop?.player!!.layoutParams
                        topParams!!.width = dip2px(190f)
                        videoPlayerTop?.player!!.layoutParams = topParams
                        val botParams = videoPlayerTop?.player!!.layoutParams
                        botParams!!.width = dip2px(190f)
                        videoPlayerBot?.player!!.layoutParams = botParams
                    }
                }
            }
        })
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
                        if (needUpdateUiState) {
                            initOperateButton()
                        } else if (videoPlayerTop?.player!!.isPlaying) {
                            videoPlayerTop?.player!!.release()
                            videoPlayerBot?.player!!.release()
                            videoPlayerTop?.player!!.setUrl(videoPath)
                            videoPlayerBot?.player!!.setUrl(videoPath)
                            videoPlayerTop?.player!!.start()
                            videoPlayerBot?.player!!.start()
                        }
                    }
                }
            }
        }
    }

    private fun initOperateButton() {
        if (user == null) return
        if (topAnimation.isStarted){
            topAnimation.end()
            botAnimation.end()
        }
        videoPlayerTop?.player!!.release()
        videoPlayerBot?.player!!.release()
        val layoutParamsTop =  videoPlayerTop?.layoutParams as ConstraintLayout.LayoutParams
        layoutParamsTop.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParamsTop.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParamsTop.marginEnd = 0
        layoutParamsTop.marginStart = 0
        videoPlayerTop?.layoutParams = layoutParamsTop
        val layoutParamsBot =  videoPlayerBot?.layoutParams as ConstraintLayout.LayoutParams
        layoutParamsBot.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParamsBot.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        layoutParamsBot.marginEnd = 0
        layoutParamsBot.marginStart = 0
        videoPlayerBot?.layoutParams = layoutParamsBot
        restoreButtonState()
        playVideoUiState(false)
        chooseVideo?.isEnabled = true
        query?.isEnabled = true
        end?.isEnabled = false
        addImg?.isEnabled = false
        binocularVision?.isEnabled = false
        convergenceValue = 0
        outreachValue = 0
        if ((user?.setValues != null && user?.setValues!!.size > 0 && !TextUtils.isEmpty(videoPath)) || user?.videoProgress!! > 0){
            mSetValue = user?.setValues!![0]
            playVideoUiState(true)
        }
        convergenceSet?.isEnabled = !TextUtils.isEmpty(videoPath)
    }

    private fun playVideoUiState(isEnabled: Boolean){
        convergence?.isEnabled = isEnabled
        outreach?.isEnabled = isEnabled
        flexible?.isEnabled = isEnabled
        limit?.isEnabled = isEnabled
        jijilingji?.isEnabled = isEnabled
        waiwaiwailing?.isEnabled = isEnabled
        waiwailingji?.isEnabled = isEnabled
        huihuilingji?.isEnabled = isEnabled
        if (isEnabled){
            videoPlayerTop?.setImgSrc(getBitmap("/systemImage/kaishi.jpg"))
            videoPlayerBot?.setImgSrc(getBitmap("/systemImage/kaishi.jpg"))
        } else {
            videoPlayerTop?.setInVisibleImg()
            videoPlayerBot?.setInVisibleImg()
        }
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
    }

    private fun startPlayVideo(){
        videoPlayerTop?.player!!.setPlayerBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.video_bg
            )
        )
        videoPlayerBot?.player!!.setPlayerBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.video_bg
            )
        )
        topAnimation.cancel()
        botAnimation.cancel()
        videoPlayerTop?.setInVisibleImg()
        videoPlayerBot?.setInVisibleImg()
        if (videoPlayerTop?.player!!.currentPlayState == VideoView.STATE_IDLE){
            videoPlayerTop?.player!!.release()
            videoPlayerBot?.player!!.release()
            videoPlayerTop?.player!!.setUrl(videoPath)
            videoPlayerBot?.player!!.setUrl(videoPath)
            val layoutParamsTop =  videoPlayerTop?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsTop.marginEnd = 0
            layoutParamsTop.marginStart = 0
            videoPlayerTop?.layoutParams = layoutParamsTop
            val layoutParamsBot =  videoPlayerBot?.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsBot.marginStart = 0
            layoutParamsBot.marginEnd = 0
            videoPlayerBot?.layoutParams = layoutParamsBot
        }
        videoPlayerTop?.player!!.start()
        videoPlayerBot?.player!!.start()
        convergenceValue = mSetValue!!.convergence
        outreachValue = mSetValue!!.outreach
        convergenceSet?.isEnabled = false
        accelerate?.isEnabled = true
        moderate?.isEnabled = true
        continued?.isEnabled = true
        continued?.text = "暂停"
        end?.isEnabled = true
        addImg?.isEnabled = true
    }

    private fun stopPlayVideo(){
        videoPlayerTop?.player!!.setPlayerBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        videoPlayerBot?.player!!.setPlayerBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        videoPlayerTop?.player!!.pause()
        videoPlayerBot?.player!!.pause()
        videoPlayerTop?.setImgSrc(getBitmap("/systemImage/bodong.jpg"))
        videoPlayerBot?.setImgSrc(getBitmap("/systemImage/bodong.jpg"))
        accelerate?.isEnabled = false
        moderate?.isEnabled = false
        continued?.isEnabled = false
        continued?.text = "继续"
    }

    private fun initAnimation(){
        topAnimation = ObjectAnimator()
        topAnimation.target = videoPlayerTop
        topAnimation.setPropertyName("translationX")
        topAnimation.interpolator = LinearInterpolator()
        botAnimation = ObjectAnimator()
        botAnimation.target = videoPlayerBot
        botAnimation.setPropertyName("translationX")
        botAnimation.interpolator = LinearInterpolator()
        topAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                stopPlayVideo()
            }
        })
    }

    private fun startConvergence(){
        startPlayVideo()
        speed = 10000
        topAnimation.setFloatValues(0f, (convergenceValue * -1).toFloat(), 0f)
        topAnimation.duration = speed
        topAnimation.repeatCount = 15
        botAnimation.setFloatValues(0f, convergenceValue.toFloat(), 0f)
        botAnimation.duration = speed
        botAnimation.repeatCount = 15
        topAnimation.start()
        botAnimation.start()
    }

    private fun startOutreach(){
        startPlayVideo()
        speed = 6000
        topAnimation.setFloatValues(0f, outreachValue.toFloat(), 0f)
        topAnimation.duration = speed
        topAnimation.repeatCount = 19
        botAnimation.setFloatValues(0f, (outreachValue * -1).toFloat(), 0f)
        botAnimation.duration = speed
        botAnimation.repeatCount = 19
        topAnimation.start()
        botAnimation.start()
    }

    private fun startFlexible(){
        startPlayVideo()
        val valueList = FloatArray(1441)
        for (i in 0..1439){
            when (i) {
                in 0..320 -> {
                    if ((i / 10) % 2 == 0) {
                        valueList[i] = (outreachValue.toFloat() * (0.3f))
                    } else {
                        valueList[i] = (convergenceValue.toFloat() * (0.3f) * -1)
                    }
                }
                in 320..799 -> {
                    if (((i - 320) / 15) % 2 == 0) {
                        valueList[i] = (outreachValue.toFloat() * (0.5f))
                    } else {
                        valueList[i] = (convergenceValue.toFloat() * (0.5f) * -1)
                    }
                }
                in 800..1439 -> {
                    if (((i - 800) / 20) % 2 == 0) {
                        valueList[i] = (outreachValue.toFloat() * (0.7f))
                    } else {
                        valueList[i] = (convergenceValue.toFloat() * (0.7f) * -1)
                    }
                }
            }
        }
        valueList[1440] = 0f
        topAnimation.setFloatValues(*valueList)
        topAnimation.duration = 144000
        topAnimation.startDelay = 800
        for (i in 0..1439){
            when (i) {
                in 0..320 -> {
                    if ((i / 10) % 2 == 0) {
                        valueList[i] = (convergenceValue.toFloat() * (0.3f) * -1)
                    } else {
                        valueList[i] = (outreachValue.toFloat() * (0.3f))
                    }
                }
                in 320..799 -> {
                    if (((i - 320) / 15) % 2 == 0) {
                        valueList[i] = (convergenceValue.toFloat() * (0.5f) * -1)
                    } else {
                        valueList[i] = (outreachValue.toFloat() * (0.5f))
                    }
                }
                in 800..1439 -> {
                    if (((i - 800) / 20) % 2 == 0) {
                        valueList[i] = (convergenceValue.toFloat() * (0.7f) * -1)
                    } else {
                        valueList[i] = (outreachValue.toFloat() * (0.7f))
                    }
                }
            }
        }
        botAnimation.setFloatValues(*valueList)
        botAnimation.duration = 144000
        botAnimation.startDelay = 800
        topAnimation.start()
        botAnimation.start()
    }

    private fun startLimit(){
        startPlayVideo()
        speed = 16000
        topAnimation.setFloatValues(
            0f,
            (convergenceValue * -1).toFloat(),
            0f,
            outreachValue.toFloat(),
            0f
        )
        topAnimation.duration = speed
        topAnimation.repeatCount = 10
        botAnimation.setFloatValues(
            0f,
            outreachValue.toFloat(),
            0f,
            (convergenceValue * -1).toFloat(),
            0f
        )
        botAnimation.duration = speed
        botAnimation.repeatCount = 10
        topAnimation.start()
        botAnimation.start()
    }






    private fun getBitmap(name: String): Bitmap? {
        return BitmapFactory.decodeFile(PATH + name)
    }

    private fun dip2px(dipValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    private fun getAllImgPath() {
        val file1 = File("$PATH/videoImage")
        val tempList1 = file1.listFiles()
        if (tempList1 != null){
            for (f in tempList1) {
                imgListPath1.add("/videoImage/" + f.name)
            }
        }
        val file2 = File("$PATH/sys")
        val tempList2 = file2.listFiles()
        if (tempList2 != null){
            for (f in tempList2) {
                imgListPath2.add("/sys/" + f.name)
            }
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