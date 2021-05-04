package com.example.weatherapp.manageres.realm

import com.example.weatherapp.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject

class RealmManager : IRealmManager {

    private val realm = Realm.getDefaultInstance()

    override fun <E : RealmObject> insertAsync(`object`: E): Completable {

        val isNotEmpty = realm.where(UserProfile::class.java).findAll().isNotEmpty()
        if (isNotEmpty) return Completable.complete()

        var objectFromRealm: E? = null
        realm.executeTransaction { realm ->
            objectFromRealm = realm.copyToRealmOrUpdate(`object`)
        }

        return if (objectFromRealm == null) Completable.error(Throwable()) else Completable.complete()
    }

    override fun getUserProfileDataChanges(): Flowable<UserProfile?> =
        realm.where(UserProfile::class.java)
            .findAllAsync()
            .asFlowable()
            .filter { it.isNotEmpty() }
            .map { it.first() }

    override fun setIsFahrenheit(): Completable =
        Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = getObjectAsync(realm, UserProfile::class.java)
                user.isFahrenheit = user.isFahrenheit.not()
                emitter.onComplete()
            }
        }

    override fun addCityId(id: Long): Completable =
        Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = getObjectAsync(realm, UserProfile::class.java)
                user.idsIgnoredList.add(id)
                emitter.onComplete()
            }
        }

    override fun clearIdsList() : Completable =
        Completable.create { emitter ->
            this.realm.executeTransaction { realm: Realm ->
                val user = getObjectAsync(realm, UserProfile::class.java)
                user.idsIgnoredList.clear()
                emitter.onComplete()
            }
        }


    override fun getIdsList(): RealmList<Long>? {
        val user = realm.where(UserProfile::class.java).findAll().firstOrNull()

        return user?.idsIgnoredList
    }

    private fun <E : RealmObject> getObjectAsync(realm: Realm, type: Class<E>) =
        realm.where(type).findFirstAsync()
}