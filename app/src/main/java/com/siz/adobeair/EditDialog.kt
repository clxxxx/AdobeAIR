package com.siz.adobeair

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

/**
 *
 * @author zhoudy
 * @time 2023/5/23 15:47
 */
class EditDialog(context: Context, var msg : String) : Dialog(context, R.style.dialog) {

    private lateinit var message : TextView
    private lateinit var editText: EditText
    private lateinit var errorMessage : TextView
    private lateinit var confirm : Button
    private lateinit var cancel : Button

    private var dialogInterface : DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setCanceledOnTouchOutside(false)
        message = findViewById(R.id.message)
        if(!TextUtils.isEmpty(msg)){
            message.text = msg
        }
        errorMessage = findViewById(R.id.error_message)
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

    fun setErrorMessage(msg : String){
        errorMessage.text = msg
    }

    fun getEditText() : EditText{
        return editText
    }

    fun setInterface(inf : DialogInterface){
        dialogInterface = inf
    }

    interface DialogInterface{
        fun onConfirm(editText : String)
    }

}