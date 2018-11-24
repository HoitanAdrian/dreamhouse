package ch.dreamhouse.injection.component

import ch.dreamhouse.injection.module.NetworkModule
import ch.dreamhouse.viewmodels.ArticleListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified ArticleListViewModel.
     * @param articleListViewModel ArticleListViewModel in which to inject the dependencies
     */
    fun inject(articleListViewModel: ArticleListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}