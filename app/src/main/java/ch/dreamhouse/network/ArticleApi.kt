package ch.dreamhouse.network

import ch.dreamhouse.models.ArticleList
import io.reactivex.Observable
import retrofit2.http.GET

interface ArticleApi {
    /**
     * Get a list of articles from the articles domain API
     */
    @GET("/properties")
    fun getArticles(): Observable<ArticleList>
}