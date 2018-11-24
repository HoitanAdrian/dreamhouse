package ch.dreamhouse.models

/**
 * Provides a model for a request that returns a list of available articles
 * @property resultCount Total number of articles available
 * @property items A list of articles
 */
data class ArticleList(val resultCount: Int, val items: List<Article>)