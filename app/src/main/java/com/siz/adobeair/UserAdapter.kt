package com.siz.adobeair

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.siz.adobeair.model.User

/**
 *
 * @author zhoudy
 * @time 2023/5/23 14:12
 */
class UserAdapter : BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_user) {

    override fun convert(helper: BaseViewHolder, item: User) {
        var userInfo = item.name
        if (!TextUtils.isEmpty(item.videoName)){
            userInfo = "$userInfo  (上次记录：${item.videoName}  ${item.videoProgress})"
        }
        helper.setText(R.id.user_info, userInfo)
    }
}