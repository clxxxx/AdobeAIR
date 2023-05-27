package com.siz.adobeair

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.siz.adobeair.model.SetValue
import com.siz.adobeair.model.User

/**
 *
 * @author zhoudy
 * @time 2023/5/27 11:54
 */
class RecordAdapter : BaseQuickAdapter<SetValue, BaseViewHolder>(R.layout.item_user) {

    override fun convert(helper: BaseViewHolder, item: SetValue) {
        helper.setText(R.id.user_info, "会聚值：0 外展值：0 设置时间：2023-3-25")
    }
}