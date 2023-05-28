package com.siz.adobeair

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class VideoAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_video) {

    var selectedGroup = -1

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.name, item)
        helper.getView<TextView>(R.id.name).isSelected = helper.adapterPosition == selectedGroup
    }
}