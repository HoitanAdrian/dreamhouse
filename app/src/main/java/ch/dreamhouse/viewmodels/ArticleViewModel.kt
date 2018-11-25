package ch.dreamhouse.viewmodels

import android.arch.lifecycle.MutableLiveData
import ch.dreamhouse.models.Article
import ch.dreamhouse.models.database.ArticleFavoritesHelper
import ch.dreamhouse.viewmodels.base.BaseViewModel

class ArticleViewModel: BaseViewModel() {
    private var articleId: Long = 0
    private var favorite: Boolean = false

    val articleTitle = MutableLiveData<String>()
    val articleLocation = MutableLiveData<String>()
    val articlePriceFormatted = MutableLiveData<String>()
    val articlePictures = MutableLiveData<List<String>>()
    val isFavorite = MutableLiveData<Boolean>()

    fun bind(article: Article){
        articleId = article.advertisementId
        articleTitle.value = article.title
        articleLocation.value = "${article.city}, ${article.street}"
        articlePriceFormatted.value = "${article.currency} ${article.price}.-"
        articlePictures.value = article.pictures

        // Set the initial favorite state
        setFavorite(ArticleFavoritesHelper.isFavorite(articleId))
    }

    private fun setFavorite(isFavorite: Boolean) {
        favorite = isFavorite
        this.isFavorite.value = isFavorite
        ArticleFavoritesHelper.setFavorite(articleId, isFavorite)
    }

    fun toggleFavorite() {
        setFavorite(!favorite)
    }

    fun getFavoriteVisibility(): MutableLiveData<Boolean> {
        return isFavorite
    }
}