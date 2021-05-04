package com.example.weatherapp.data

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class UserProfile(
    @PrimaryKey private var key: String = UUID.randomUUID().toString(),
    var isFahrenheit: Boolean = true,
    var idsIgnoredList: RealmList<Long> = RealmList<Long>()
) :
    RealmObject() {
}