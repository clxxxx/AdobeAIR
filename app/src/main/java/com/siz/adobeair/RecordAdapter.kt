package com.siz.adobeair

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.siz.adobeair.model.SetValue
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @author zhoudy
 * @time 2023/5/27 11:54
 */
class RecordAdapter : BaseQuickAdapter<SetValue, BaseViewHolder>(R.layout.item_user) {

    override fun convert(helper: BaseViewHolder, item: SetValue) {
        helper.setText(
            R.id.user_info, "会聚值：${item.convergence}  外展值：${item.outreach}  设置时间：${
                formatData(
                    item.setTime
                )
            }"
        )
    }

    private fun formatData(timeStamp: Long) : String{
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(Date(timeStamp))
    }

}