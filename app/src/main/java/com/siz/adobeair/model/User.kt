package com.siz.adobeair.model

import io.realm.RealmObject
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

/**
 *
 * @author zhoudy
 * @time 2023/5/23 10:29
 */
open class User(@PrimaryKey
                var id: Long = 0,
                var name: String = "",
                var groupId: Long = 0): RealmObject(){

}