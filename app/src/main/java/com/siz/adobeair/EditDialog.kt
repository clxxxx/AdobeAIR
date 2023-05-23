package com.siz.adobeair

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

/**
 *
 * @author zhoudy
 * @time 2023/5/23 15:47
 */
class EditDialog(context: Context) : Dialog(context, R.style.dialog) {

    private lateinit var message : TextView
    private lateinit var editText: EditText
    private lateinit var confirm : Button
    private lateinit var cancel : Button

    private var dialogInterface : DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)
        setCanceledOnTouchOutside(false)
        message = findViewById(R.id.message)
        editText = findViewById(R.id.edit)
        confirm = findViewById(R.id.confirm)
        cancel = findViewById(R.id.cancel)
        confirm.setOnClickListener {
            dialogInterface?.onConfirm(editText.text.toString())
        }
        cancel.setOnClickListener {
            dismiss()
        }
    }

    fun setMessage(msg : String){
        message.text = msg
    }

    fun setInterface(inf : DialogInterface){
        dialogInterface = inf
    }

    interface DialogInterface{
        fun onConfirm(editText : String)
    }

}