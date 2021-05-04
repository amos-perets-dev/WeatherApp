package com.example.weatherapp.repo

import com.example.weatherapp.data.UserProfile
import com.example.weatherapp.manageres.cities.ICitiesListManager
import com.example.weatherapp.manageres.realm.IRealmManager
import com.example.weatherapp.screens.cities_list.model.ICity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class CitiesRepo @Inject constructor(
    private val citiesListManager: ICitiesListManager,
    private val realmManager: IRealmManager
) : ICitiesRepo {

    private val citiesMap = hashMapOf<Long, ICity>()
    private var rawList = listOf<ICity>()
    private var copyRawList = listOf<ICity>()

    private val citiesList = BehaviorSubject.create<List<ICity>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable
            .addAll(
                realmManager
                    .insertAsync(UserProfile())
                    .subscribe({}, (Throwable::printStackTrace))
            )
    }

    override fun addCitiesList(citiesDataList: MutableList<ICity>): Completable {

        return realmManager
            .getUserProfileDataChanges()
            .firstOrError()
            .map { it.idsIgnoredList }
            .map { idsList ->
                rawList = citiesDataList
                citiesDataList.filter { item -> idsList.contains(item.getCityId()).not() }
            }
            .doOnEvent { data, error ->
                citiesList.onNext(data)
                copyRawList = data
                data.forEach {
                    citiesMap[it.getCityId()] = it
                }

            }
            .ignoreElement()
    }

    override fun getCitiesList(): Observable<List<ICity>> {
        return citiesList
            .hide()
            .subscribeOn(Schedulers.io())
//            .distinctUntilChanged()
    }

    override fun getCityById(id: Long): ICity? {
        return citiesMap[id]
    }

    override fun generateListBySearch(input: String) {
        val list = if (input.isEmpty()) {
            copyRawList
        } else {
            copyRawList
                .filter {
                    it.getLocationName().toLowerCase().contains(input.toLowerCase())
                }
        }
        citiesList.onNext(list)
    }

    override fun deleteItem(cityId: Long): Completable {
        return realmManager.addCityId(cityId)
            .doOnEvent {
                val indexOf = copyRawList.indexOf(citiesMap[cityId])
                if(indexOf != -1){
                    (copyRawList as ArrayList).removeAt(indexOf)
                }
            }
    }

    override fun clearIdsList(): Completable {

//        if (copyRawList.size == rawList.size) return Completable.complete()

        return realmManager.clearIdsList()
            .doOnEvent {
                citiesList.onNext(rawList)
                copyRawList = ArrayList(rawList)
            }
    }

    override fun getCitiesData(): Completable? {
        return citiesListManager
            .getCitiesData()
            ?.flatMapCompletable { addCitiesList(it) }
    }

    override fun getCityData(id: Long): Single<ICity?>? {
        return citiesListManager.getCityData(id)
    }

    override fun dispose() {
        compositeDisposable.clear()
    }

}