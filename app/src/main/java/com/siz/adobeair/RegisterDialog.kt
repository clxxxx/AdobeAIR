package com.siz.adobeair

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.util.*


/**
 *
 * @author zhoudy
 * @time 2023/6/6 14:00
 */
class RegisterDialog(context: Context) : Dialog(context, R.style.dialog) {

    private val mContext: Context = context
    private lateinit var info : TextView
    private lateinit var editText: EditText
    private lateinit var errorMessage : TextView
    private lateinit var confirm : Button
    private lateinit var password : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_register)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        info = findViewById(R.id.info)
        errorMessage = findViewById(R.id.error_message)
        editText = findViewById(R.id.edit)
        confirm = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            val editPassword = editText.text.toString()
            if (TextUtils.isEmpty(editPassword)){
                errorMessage.text = "请输入激活码"
            }
            if (TextUtils.equals(editPassword, password)){
                val registerFile = File(mContext.filesDir, "register.txt")
                val fis = FileInputStream(registerFile)
                val length = fis.available()
                val buffer = ByteArray(length)
                fis.read(buffer)
                val info = String(buffer).replace("/0","/1")
                val fileWriter = FileWriter(registerFile, false)
                fileWriter.write(info)
                fileWriter.flush()
                fileWriter.close()
                dismiss()
            } else {
                errorMessage.text = "激活码错误"
            }
        }
        var deviceId = getUUID()
        val registerFile = File(mContext.filesDir, "register.txt")
        if (!registerFile.exists()){
            registerFile.createNewFile()
            val fileWriter = FileWriter(registerFile, false)
            info.text = "设备ID : $deviceId"
            fileWriter.write("$deviceId/0")
            fileWriter.flush()
            fileWriter.close()
        } else {
            val fis = FileInputStream(registerFile)
            val length = fis.available()
            val buffer = ByteArray(length)
            fis.read(buffer)
            deviceId = String(buffer)
            deviceId = deviceId.substring(0, deviceId.length - 2)
            info.text = "设备ID : $deviceId"
        }
        getPassword(MD5Util.getMd5String("$deviceId-Adobe-AIR-9527").toUpperCase(Locale.ROOT)).also {
            password = it
        }
    }

    private fun getUUID(): String {
        var uuid = ""
        try {
            uuid = UUID.randomUUID().toString()
            uuid = uuid.toUpperCase()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uuid
    }

    private fun getPassword(value : String): String {
        return value[1].toString() + value[4].toString() +value[6].toString()+
                value[8].toString()+ value[10].toString() + value[30].toString()
    }
}