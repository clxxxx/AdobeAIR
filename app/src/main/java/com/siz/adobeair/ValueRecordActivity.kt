package com.siz.adobeair

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.siz.adobeair.model.SetValue
import com.siz.adobeair.model.User
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

/**
 *
 * @author zhoudy
 * @time 2023/5/27 11:33
 */
class ValueRecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_value_record)
        ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init()

        val id = intent?.getLongExtra("userId", 0)
        val realm = Realm.getDefaultInstance()
        val user = realm.where<User>().equalTo("id", id).findFirst()
        findViewById<TextView>(R.id.user_name).text = "当前用户  " + user?.name
        val record = findViewById<RecyclerView>(R.id.record)
        record.layoutManager = LinearLayoutManager(this)
        val recordAdapter = RecordAdapter()
        recordAdapter.bindToRecyclerView(record)
        recordAdapter.setNewData(user?.setValues!!.sort("setTime", Sort.DESCENDING))

        findViewById<Button>(R.id.close).setOnClickListener {
            finish()
        }

    }
}