package ch.dreamhouse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import ch.dreamhouse.utils.BASE_URL
import ch.dreamhouse.utils.ResourcesHelper
import ch.dreamhouse.viewmodels.ArticleListViewModel
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test

import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.mock

class ArticleListViewModelUnitTest {
    companion object {
        private val mockServer = MockWebServer()
        private val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))

        @BeforeClass
        @JvmStatic fun setup() {
            // Override the default "out of the box" AndroidSchedulers.mainThread() Scheduler
            //
            // This is necessary here because otherwise if the static initialization block in AndroidSchedulers
            // is executed before this, then the Android SDK dependent version will be provided instead.
            //
            // This would cause a java.lang.ExceptionInInitializerError when running the test as a
            // Java JUnit test as any attempt to resolve the default underlying implementation of the
            // AndroidSchedulers.mainThread() will fail as it relies on unavailable Android dependencies.
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

            mockServer.start()
            BASE_URL = mockServer.url("/").toString();

            lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun test_loadArticleListWithOKResponse() {
        // Set up mocked data
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(ResourcesHelper.loadJson("json/article_list_200_ok_response.json"))
        )

        // Initialize the viewmodel and load articles
        val viewModel = ArticleListViewModel()
        viewModel.loadArticles()

        viewModel.articles.observe({ lifecycle }) { articles ->
            assertEquals(1, articles?.size)
            assertEquals("test_title", articles?.get(0)?.title)
            assertEquals("test_street", articles?.get(0)?.street)
            assertEquals("test_zip", articles?.get(0)?.zip)
            assertEquals("test_city", articles?.get(0)?.city)
            assertEquals(1234, articles?.get(0)?.price)
            assertEquals("test_currency", articles?.get(0)?.currency)
            assertEquals(10, articles?.get(0)?.pictures?.size)
        }
    }

    @Test
    fun test_loadArticleListWithErrorResponse() {
        // Set up mocked data
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("")
        )

        // Initialize the viewmodel and load articles
        val viewModel = ArticleListViewModel()
        viewModel.loadArticles()

        viewModel.hasErrors.observe({ lifecycle }) { hasErrors ->
            assertEquals(true, hasErrors)
        }
    }

    inline fun <reified T> lambdaMock(): T = mock(T::class.java)

    @Test
    fun test_loadArticleListLoadingIndicator() {
        // Set up mocked data
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(ResourcesHelper.loadJson("json/article_list_200_ok_response.json"))
        )

        // Initialize the viewmodel and load articles
        val viewModel = ArticleListViewModel()
        viewModel.loadArticles()

        val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)

        viewModel.loadingVisibility.observe({ lifecycle }) { isLoading ->
            assertEquals(false, isLoading)
        }
    }
}

