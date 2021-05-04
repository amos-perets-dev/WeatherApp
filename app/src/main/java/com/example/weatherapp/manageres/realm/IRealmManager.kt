package com.example.weatherapp.manageres.realm

import com.example.weatherapp.data.UserProfile
import io.reactivex.Completable
import io.reactivex.Flowable
import io.realm.RealmList
import io.realm.RealmObject

interface IRealmManager {

    /**
     * Insert the user profile
     */
    fun <E : RealmObject> insertAsync(`object`: E): Completable

    /**
     * Get the user profile data from the DB
     */
    fun getUserProfileDataChanges(): Flowable<UserProfile?>

    /**
     * Set is the temp' symbol is F / C
     *
     * @return [Completable]
     */
    fun setIsFahrenheit(): Completable

    /**
     * Add the city ID that needs to be filtered
     *
     * @return [Completable]
     */
    fun addCityId(id: Long): Completable

    /**
     * Get the cities ids that need to be filtered
     *
     * @return [RealmList]
     */
    fun getIdsList(): RealmList<Long>?

    /**
     * Cleaer the cities ids from the DB
     *
     * @return [RealmList]
     */
    fun clearIdsList(): Completable

}