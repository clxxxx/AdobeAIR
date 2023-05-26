package com.siz.adobeair.model

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects

/**
 *
 * @author zhoudy
 * @time 2023/5/26 11:03
 */
open class SetValue : RealmObject() {

    var convergence : Int = 0
    var outreach : Int = 0
    var setTime : Long = 0

    @LinkingObjects("setValues")
    val owners: RealmResults<User>? = null
}