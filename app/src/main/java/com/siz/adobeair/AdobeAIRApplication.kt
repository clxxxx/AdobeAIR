package com.siz.adobeair

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

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
    }

}