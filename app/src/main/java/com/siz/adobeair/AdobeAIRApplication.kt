package com.siz.adobeair

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import xyz.doikki.videoplayer.BuildConfig
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory
import xyz.doikki.videoplayer.player.VideoViewConfig
import xyz.doikki.videoplayer.player.VideoViewManager

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

        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(
            VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG) //调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
                .build()
        )

    }

}