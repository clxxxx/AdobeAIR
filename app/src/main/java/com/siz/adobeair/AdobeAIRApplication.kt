package com.siz.adobeair

import android.app.Application
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import io.realm.Realm
import io.realm.RealmConfiguration
import tv.danmaku.ijk.media.player.IjkMediaPlayer

/**
 *
 * @author zhoudy
 * @time 2023/5/22 19:45
 */
class AdobeAIRApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val configuration = RealmConfiguration.Builder().allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true).name("test.realm").build()
        Realm.setDefaultConfiguration(configuration)

        //ijk关闭log
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL)
//        GSYVideoType.setRenderType(GSYVideoType.SUFRACE)
    }

}