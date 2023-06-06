package com.siz.adobeair

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button

/**
 *
 * @author zhoudy
 * @time 2023/6/6 14:00
 */
class NoticeDialog(context: Context) : Dialog(context, R.style.dialog) {

    private lateinit var confirm : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_notice)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setCanceledOnTouchOutside(false)
        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            dismiss()
        }
    }

}