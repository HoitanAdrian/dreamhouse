package ch.dreamhouse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ch.dreamhouse.databinding.ActivityMainBinding
import ch.dreamhouse.ui.adapters.ArticleListAdapter
import ch.dreamhouse.viewmodels.ArticleListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticleListViewModel
    private lateinit var binding: ActivityMainBinding
    private val postListAdapter: ArticleListAdapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset the theme to the default one from the launcher theme.
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.articlesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this).get(ArticleListViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.loadArticles()

        articles_list.adapter = postListAdapter
        viewModel.articles.observe(this, Observer { articles -> postListAdapter.updatePostList(articles!!) })
        viewModel.hasErrors.observe(this, Observer { hasError -> article_list_error_layout.visibility = if (hasError!!) View.VISIBLE else View.GONE })
    }
}
