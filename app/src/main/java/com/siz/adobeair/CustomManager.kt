package com.siz.adobeair

import android.text.TextUtils
import com.shuyu.gsyvideoplayer.GSYVideoBaseManager
import com.shuyu.gsyvideoplayer.player.IPlayerManager
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager


/**
 *
 * @author zhoudy
 * @time 2023/6/3 15:07
 */
class CustomManager : GSYVideoBaseManager() {

    companion object{
        var sMap: HashMap<String, CustomManager> = HashMap()
        /**
         * 单例管理器
         */
        @Synchronized
        fun instance(): HashMap<String, CustomManager> {
            return sMap
        }

        /**
         * 单例管理器
         */
        @Synchronized
        fun getCustomManager(key: String): CustomManager {
            check(!TextUtils.isEmpty(key)) { "key not be empty" }
            var customManager: CustomManager? = sMap[key]
            if (customManager == null) {
                customManager = CustomManager()
                sMap[key] = customManager
            }
            return customManager
        }

        /**
         * 页面销毁了记得调用是否所有的video
         */
        fun releaseAllVideos(key: String) {
            if (getCustomManager(key).listener() != null) {
                getCustomManager(key).listener().onCompletion()
            }
            getCustomManager(key).releaseMediaPlayer()
        }

        fun onPauseAll() {
            if (sMap.size > 0) {
                for ((key, value) in sMap) {
                    value.onPause(key)
                }
            }
        }

        fun onResumeAll() {
            if (sMap.size > 0) {
                for ((key, value) in sMap) {
                    value.onResume(key)
                }
            }
        }

        /**
         * 恢复暂停状态
         *
         * @param seek 是否产生seek动作
         */
        fun onResumeAll(seek: Boolean) {
            if (sMap.size > 0) {
                for ((key, value) in sMap) {
                    value.onResume(key, seek)
                }
            }
        }

        fun clearAllVideo() {
            if (sMap.size > 0) {
                for ((key) in sMap) {
                    releaseAllVideos(key)
                }
            }
            sMap.clear()
        }

        fun removeManager(key: String?) {
            sMap.remove(key)
        }
    }

    init {
        init()
    }

    override fun getPlayManager(): IPlayerManager {
        return IjkPlayerManager()
    }

    /**
     * 暂停播放
     */
    fun onPause(key: String?) {
        if (getCustomManager(key!!).listener() != null) {
            getCustomManager(key).listener().onVideoPause()
        }
    }

    /**
     * 恢复播放
     */
    fun onResume(key: String?) {
        if (getCustomManager(key!!).listener() != null) {
            getCustomManager(key).listener().onVideoResume()
        }
    }


    /**
     * 恢复暂停状态
     *
     * @param seek 是否产生seek动作,直播设置为false
     */
    fun onResume(key: String?, seek: Boolean) {
        if (getCustomManager(key!!).listener() != null) {
            getCustomManager(key).listener().onVideoResume(seek)
        }
    }



}