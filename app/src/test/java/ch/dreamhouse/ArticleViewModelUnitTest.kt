package ch.dreamhouse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import ch.dreamhouse.models.Article
import ch.dreamhouse.viewmodels.ArticleViewModel
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class ArticleViewModelUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun test_bindArticle() {

        // Create a new article view model
        val pictures = listOf("test_picture1", "test_picture2")
        val viewModel = ArticleViewModel()
        viewModel.bind(Article(116237026, "test_title", "test_street", "test_zip", "test_city", 1234, "test_currency", pictures))

        // Check if all data is binded and formatted correctly
        assertEquals("test_title", viewModel.articleTitle.value)
        assertEquals("test_city, test_street", viewModel.articleLocation.value)
        assertEquals("test_currency 1234.-", viewModel.articlePriceFormatted.value)
        assertEquals(2, viewModel.articlePictures.value?.size)
        assertEquals("test_picture1", viewModel.articlePictures.value?.get(0))
        assertEquals("test_picture2", viewModel.articlePictures.value?.get(1))
    }
}

