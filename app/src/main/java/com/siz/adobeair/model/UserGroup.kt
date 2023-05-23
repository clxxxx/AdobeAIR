package com.siz.adobeair.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 *
 * @author zhoudy
 * @time 2023/5/23 14:37
 */
open class UserGroup (@PrimaryKey
                 var id: Long = 0,
                 var groupName: String = ""): RealmObject(){

}