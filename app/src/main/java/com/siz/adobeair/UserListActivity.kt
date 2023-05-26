package com.siz.adobeair

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.siz.adobeair.model.User
import com.siz.adobeair.model.UserGroup
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 *
 * @author zhoudy
 * @time 2023/5/23 11:18
 */
open class UserListActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private var register : Button? =null
    private var createGroup : Button? =null
    private var edit : Button? =null
    private lateinit var userGroup : RecyclerView
    private lateinit var users : RecyclerView
    private lateinit var userGroupAdapter: UserGroupAdapter
    private lateinit var userAdapter: UserAdapter
    private var userGroupList = mutableListOf<UserGroup>()
    private var userList = mutableListOf<User>()

    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        userGroup = findViewById(R.id.user_group)
        userGroup.layoutManager = LinearLayoutManager(this)
        userGroupAdapter = UserGroupAdapter()
        userGroupAdapter.bindToRecyclerView(userGroup)
        userGroupAdapter.setOnItemClickListener { _, _, position ->
            val groupId = userGroupList[position].id
            findUsersByGroup(groupId)
            userGroupAdapter.selectedGroup = position
            userGroupAdapter.notifyDataSetChanged()
        }
        users = findViewById(R.id.user)
        users.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter()
        userAdapter.bindToRecyclerView(users)
        userAdapter.setNewData(userList)
        userAdapter.setOnItemClickListener { _, _, position ->
            intent.putExtra("user",userList[position])
            setResult(RESULT_OK, intent)
            finish()
        }
//        userAdapter.setOnItemLongClickListener{ _, _, position ->
//
//
//            return@setOnItemLongClickListener true
//        }
//        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallBack())
//        itemTouchHelper.attachToRecyclerView(users)
        register = findViewById(R.id.register)
        register?.setOnClickListener {
            val editDialog = EditDialog(this, "请输入用户名")
            editDialog.setInterface(object : EditDialog.DialogInterface {
                override fun onConfirm(editText: String) {
                    if (!TextUtils.isEmpty(editText) && editText.length > 1) {
                        hiddenKeyboard(editDialog.getEditText())
                        realm.executeTransaction { realm ->
                            val user = realm.createObject<User>(userList.size)
                            user.name = editText
                            user.groupId = userGroupList[userGroupAdapter.selectedGroup].id
                        }
                        findUsersByGroup(userGroupList[userGroupAdapter.selectedGroup].id)
                        editDialog.dismiss()
                    } else {
                        editDialog.setErrorMessage("用户名小于2位")
                    }
                }
            })
            editDialog.show()
        }
        createGroup = findViewById(R.id.create_group)
        createGroup?.setOnClickListener {
            val editDialog = EditDialog(this, "请输入文件夹名")
            editDialog.setInterface(object : EditDialog.DialogInterface {
                override fun onConfirm(editText: String) {
                    if (!TextUtils.isEmpty(editText) && editText.length > 1) {
                        hiddenKeyboard(editDialog.getEditText())
                        realm.executeTransaction { realm ->
                            val userGroup = realm.createObject<UserGroup>(userGroupList.size)
                            userGroup.groupName = editText
                        }
                        userGroupList = realm.where<UserGroup>().findAll()
                        userGroupAdapter.notifyDataSetChanged()
                        editDialog.dismiss()
                    } else {
                        editDialog.setErrorMessage("文件夹名小于2位")
                    }
                }
            })
            editDialog.show()
        }
        edit = findViewById(R.id.edit)
        edit?.setOnClickListener {
            if (register!!.isEnabled){
                edit?.text = "取消编辑"
                register?.isEnabled = false
                createGroup?.isEnabled = false
            } else {
                edit?.text = "编辑"
                register?.isEnabled = true
                createGroup?.isEnabled = true
            }
        }
        findViewById<Button>(R.id.close).setOnClickListener {
            finish()
        }

        realm = Realm.getDefaultInstance()
        userGroupList = realm.where<UserGroup>().findAll()

        if (userGroupList.isEmpty()){
            realm.executeTransaction { realm ->
                val userGroup = realm.createObject<UserGroup>(0)
                userGroup.groupName = "默认"
            }
            userGroupList = realm.where<UserGroup>().findAll()
        }
        userGroupAdapter.setNewData(userGroupList)
        findUsersByGroup(userGroupList[0].id)
    }

    private fun findUsersByGroup(group: Long){
        userList = realm.where<User>().equalTo("groupId", group).findAll()
        userAdapter.replaceData(userList)
    }

    /**
     * 隐藏键盘
     *
     * @return 隐藏键盘结果
     */
    private fun hiddenKeyboard(view: View) {
        if (currentFocus != null && currentFocus!!.windowToken != null) {
            if (imm != null) {
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

}