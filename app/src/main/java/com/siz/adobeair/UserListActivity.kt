package com.siz.adobeair

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.barlibrary.BarHide
import com.gyf.barlibrary.ImmersionBar
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
class UserListActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    private lateinit var userGroup : RecyclerView
    private lateinit var users : RecyclerView
    private lateinit var userGroupAdapter: UserGroupAdapter
    private lateinit var userAdapter: UserAdapter
    private var userGroupList = mutableListOf<UserGroup>()
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        ImmersionBar.with(this).fullScreen(true).hideBar(BarHide.FLAG_HIDE_BAR).init()

        userGroup = findViewById(R.id.user_group)
        userGroup.layoutManager = LinearLayoutManager(this)
        userGroupAdapter = UserGroupAdapter()
        userGroupAdapter.bindToRecyclerView(userGroup)
        users = findViewById(R.id.user)
        users.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter()
        userAdapter.bindToRecyclerView(users)

        findViewById<Button>(R.id.register).setOnClickListener {
            val editDialog = EditDialog(this)
            editDialog.setMessage("请输入用户名")
//            editDialog.setInterface(object : DialogInterface{
//
//            })
            editDialog.show()
        }
        findViewById<Button>(R.id.create_group).setOnClickListener {

        }
        findViewById<Button>(R.id.edit).setOnClickListener {
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

    private fun findUsersByGroup(group : Long){
        userList = realm.where<User>().equalTo("groupId", group).findAll()
        userAdapter.notifyDataSetChanged()
    }

}