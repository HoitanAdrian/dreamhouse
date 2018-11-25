package ch.dreamhouse.viewmodels

import android.arch.lifecycle.MutableLiveData
import ch.dreamhouse.models.Article
import ch.dreamhouse.models.ArticleList
import ch.dreamhouse.network.ArticleApi
import ch.dreamhouse.viewmodels.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleListViewModel: BaseViewModel() {
    @Inject
    lateinit var articleApi: ArticleApi

    private lateinit var subscription: Disposable

    val articles: MutableLiveData<List<Article>> = MutableLiveData()
    val hasErrors: MutableLiveData<Boolean> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    fun loadArticles() {
        subscription = articleApi.getArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticlesStart() }
            .doOnTerminate { onRetrieveArticlesFinish() }
            .subscribe(
                { result -> onRetrieveArticlesSuccess(result) },
                { onRetrieveArticlesError() }
            )
    }

    private fun onRetrieveArticlesStart(){
        loadingVisibility.value = true
        hasErrors.value = false
    }

    private fun onRetrieveArticlesFinish(){
        loadingVisibility.value = false
    }

    private fun onRetrieveArticlesSuccess(postList:ArticleList){
        articles.value = postList.items
    }

    private fun onRetrieveArticlesError(){
        hasErrors.value = true
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}