package com.siz.adobeair

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar

/**
 *
 * @author zhoudy
 * @time 2023/5/27 14:50
 */
open class SplashActivity : AppCompatActivity() {

    companion object {
        private val S_HANDLER: Handler = Handler(Looper.getMainLooper())
    }

    private var onCreateFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateFlag = true
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_BAR).init()

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (onCreateFlag && hasFocus) {
            onCreateFlag = false
            S_HANDLER.postDelayed(this::onFullyDrawn, 800)
        }
    }

    @CallSuper
    protected fun onFullyDrawn() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
        finish()
    }
}