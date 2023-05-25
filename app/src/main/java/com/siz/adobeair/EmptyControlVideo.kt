package com.siz.adobeair

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


/**
 *
 * @author zhoudy
 * @time 2023/5/24 19:18
 */
class EmptyControlVideo(context: Context, attrs: AttributeSet) : StandardGSYVideoPlayer(context, attrs) {

    private val mContext: Context = context
    private lateinit var img : ImageView

    override fun getLayoutId(): Int {
        return R.layout.empty_control_video
    }

    init {
        super.init(mContext)
        img = findViewById(R.id.img)
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



}