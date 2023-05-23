package com.siz.adobeair

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
        helper.setText(R.id.user_info, item.name)
    }
}