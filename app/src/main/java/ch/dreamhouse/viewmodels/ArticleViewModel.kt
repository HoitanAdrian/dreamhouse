package ch.dreamhouse.viewmodels

import android.arch.lifecycle.MutableLiveData
import ch.dreamhouse.models.Article
import ch.dreamhouse.viewmodels.base.BaseViewModel

class ArticleViewModel: BaseViewModel() {
    val articleTitle = MutableLiveData<String>()
    val articleLocation = MutableLiveData<String>()
    val articlePriceFormatted = MutableLiveData<String>()
    val articlePictures = MutableLiveData<List<String>>()

    fun bind(article: Article){
        articleTitle.value = article.title
        articleLocation.value = "${article.city}, ${article.street}"
        articlePriceFormatted.value = "${article.currency} ${article.price}.-"
        articlePictures.value = article.pictures
    }
}