package com.siz.adobeair

import android.text.TextUtils
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.siz.adobeair.model.User
import java.util.*

/**
 *
 * @author zhoudy
 * @time 2023/5/23 14:12
 */
class UserAdapter : BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_user) {

    override fun convert(helper: BaseViewHolder, item: User) {
        var userInfo = item.name
        if (item.videoProgress > 0 && !TextUtils.isEmpty(item.videoName)){
            userInfo = "$userInfo  (上次记录：${item.videoName!!.substring(item.videoName!!.lastIndexOf("/")+1,
                item.videoName!!.indexOf("."))}   ${formatTime(item.videoProgress / 1000 )})"
        }
        helper.setText(R.id.user_info, userInfo)
    }

    private fun formatTime(second: Long) : String{
        var timeStr = "--"
        if (second >= 0) {
            timeStr = ""
            val formatHour: Long = second / 3600 // 时,向下取整
            val formatMinute: Long = second % 3600 / 60 // 分，向下取整
            val formatSecond: Long = second % 60 // 秒
            timeStr = if (formatHour < 10) {
                timeStr + "0" + formatHour + ":"
            } else {
                "$timeStr$formatHour:"
            }
            timeStr = if (formatMinute < 10) {
                timeStr + "0" + formatMinute + ":"
            } else {
                "$timeStr$formatMinute:"
            }
            timeStr = if (formatSecond < 10) {
                timeStr + "0" + formatSecond
            } else {
                timeStr + formatSecond
            }
        }
        return timeStr
    }
}