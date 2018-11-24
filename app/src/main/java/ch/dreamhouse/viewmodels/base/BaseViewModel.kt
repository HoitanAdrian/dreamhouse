package ch.dreamhouse.viewmodels.base

import android.arch.lifecycle.ViewModel
import ch.dreamhouse.injection.component.DaggerViewModelInjector
import ch.dreamhouse.injection.component.ViewModelInjector
import ch.dreamhouse.injection.module.NetworkModule
import ch.dreamhouse.viewmodels.ArticleListViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ArticleListViewModel -> injector.inject(this)
        }
    }
}