package com.siz.adobeair

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import xyz.doikki.videoplayer.player.AbstractPlayer
import xyz.doikki.videoplayer.player.VideoView


/**
 *
 * @author zhoudy
 * @time 2023/5/24 19:18
 */
class VideoControlView(context: Context, attrs: AttributeSet) : ConstraintLayout(
    context,
    attrs
) {

    private var img : ImageView? =null
    var player : VideoView<AbstractPlayer>? =null

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.video_control_vieww, this, true)
        img = findViewById(R.id.img)
        player = findViewById(R.id.player)
        //必须设置
        player?.setEnableAudioFocus(false)
    }

    fun setImgSrc(bm: Bitmap?){
        if (bm == null) return
        img?.visibility = VISIBLE
        img?.setImageBitmap(bm)
    }

    fun setInVisibleImg(){
        img?.visibility = GONE
    }


}