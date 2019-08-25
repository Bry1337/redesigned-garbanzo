package bry1337.github.io.newsthings.injection.component

import bry1337.github.io.newsthings.injection.module.NetworkModule
import bry1337.github.io.newsthings.ui.detail.DetailViewModel
import bry1337.github.io.newsthings.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

  fun inject(homeViewModel: HomeViewModel): HomeViewModel

  fun inject(detailViewModel: DetailViewModel): DetailViewModel

  @Component.Builder
  interface Builder {
    fun build(): ViewModelInjector

    fun networkModule(networkModule: NetworkModule): Builder
  }
}