package com.siz.adobeair

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.siz.adobeair.model.UserGroup

/**
 *
 * @author zhoudy
 * @time 2023/5/23 14:12
 */
class UserGroupAdapter : BaseQuickAdapter<UserGroup, BaseViewHolder>(R.layout.item_user_group) {

    var selectedGroup = 0

    override fun convert(helper: BaseViewHolder, item: UserGroup) {
        helper.setText(R.id.group_name, item.groupName)
        helper.getView<TextView>(R.id.group_name).isSelected = helper.adapterPosition == selectedGroup
    }
}