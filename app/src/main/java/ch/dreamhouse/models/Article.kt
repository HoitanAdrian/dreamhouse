package ch.dreamhouse.models

/**
 * Provides a model for a single article item
 * @property title Title of the article
 * @property street Street of the article
 * @property zip Zip code associated with the city
 * @property city City where the article is located
 * @property price Renting or buying price of the article
 * @property currency Currency of the price stored as string
 * @property pictures A list of article pictures
 */
data class Article(val title: String, val street:String, val zip:String, val city:String, val price: Int, val currency: String, val pictures: List<String>)