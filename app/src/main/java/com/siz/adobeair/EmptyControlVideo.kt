package com.siz.adobeair

import android.content.Context
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


/**
 *
 * @author zhoudy
 * @time 2023/5/24 19:18
 */
class EmptyControlVideo(context: Context, attrs: AttributeSet) : StandardGSYVideoPlayer(
    context,
    attrs
) {

    private lateinit var img : ImageView
    private lateinit var surfaceContainer : FrameLayout

    override fun getLayoutId(): Int {
        return R.layout.empty_control_video
    }

    override fun init(context: Context) {
        super.init(context)
        img = findViewById(R.id.img)
        surfaceContainer = findViewById(R.id.surface_container)

        onAudioFocusChangeListener =
            OnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    AudioManager.AUDIOFOCUS_GAIN -> {
                    }
                    AudioManager.AUDIOFOCUS_LOSS -> {
                    }
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    }
                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    }
                }
            }
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false
        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false
        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun touchDoubleUp(e: MotionEvent?) {
        //super.touchDoubleUp();
        //不需要双击暂停
    }

    fun setImgSrc(bm: Bitmap?){
        if (bm == null) return
        img.visibility = VISIBLE
        img.setImageBitmap(bm)
    }

    fun setInVisibleImg(){
        img.visibility = GONE
    }

//    override fun getGSYVideoManager(): GSYVideoViewBridge {
//        CustomManager.getCustomManager(getKey()).initContext(context.applicationContext)
//        return CustomManager.getCustomManager(getKey())
//    }
//
//    override fun releaseVideos() {
//        CustomManager.releaseAllVideos(getKey())
//    }
//
//    private fun getKey(): String {
//        return "MultiSampleVideo$mPlayPosition$mPlayTag"
//    }

}