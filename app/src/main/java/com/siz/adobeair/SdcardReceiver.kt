package com.siz.adobeair

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 *
 * @author zhoudy
 * @time 2023/6/14 9:37
 */
class SdcardReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context, intent: Intent) {
        val action = intent.action
        Log.d("+++++++", "000000")
        if (action == Intent.ACTION_MEDIA_MOUNTED) {
            Log.d("+++++++", "sdcard mounted")
        } else if (action == Intent.ACTION_MEDIA_UNMOUNTED) {
            Log.d("+++++++", "sdcard unmounted")
        }
    }
}