package ch.dreamhouse.models.database

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Helper class that holds a list of ids that are set as favorites by the user. If an ID does not exist in this list,
 * the user didnt select it as favorite.
 * TODO: The database should be cleared at initialization from elements that surpass expiration date
 */
object ArticleFavoritesHelper {
    private var db: ArticleFavoritesDao? = null
    private var favoriteList: MutableList<ArticleFavorites> = mutableListOf()
    private lateinit var dbGetAllSubscription: Disposable

    fun initialize(db: ArticleFavoritesDao?) {
        this.db = db
        loadFavorites()
    }

    private fun loadFavorites() {
        dbGetAllSubscription = Observable.fromCallable{db?.getAll()}
            .concatMap {
                dbPostList -> Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> onRetrieveFavoritesSuccess(result) }
    }

    private fun insertFavorite(favorite: ArticleFavorites) {
        Observable.fromCallable{db?.insert(favorite)}
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun deleteFavorite(id: Long) {
        Observable.fromCallable{db?.delete(id)}
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun onRetrieveFavoritesSuccess(favorites: List<ArticleFavorites>){
        favoriteList?.addAll(favorites)
        dbGetAllSubscription.dispose()
    }

    fun isFavorite(id: Long): Boolean {
        return favoriteList.find { v -> v.id == id } != null
    }

    fun setFavorite(id: Long, isFavorite: Boolean) {
        val element = favoriteList.find { v -> v.id == id }
        if (element != null && !isFavorite) {
            favoriteList.remove(element)
            deleteFavorite(id)
        }

        if (element == null && isFavorite) {
            val favorite = ArticleFavorites(id, 0)
            favoriteList.add(favorite)
            insertFavorite(favorite)
        }
    }
}