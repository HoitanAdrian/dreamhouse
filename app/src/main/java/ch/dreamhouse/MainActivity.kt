package ch.dreamhouse

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ch.dreamhouse.viewmodels.ArticleListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Reset the theme to the default one from the launcher theme.
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ArticleListViewModel::class.java)
    }
}
