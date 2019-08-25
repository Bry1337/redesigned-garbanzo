package bry1337.github.io.newsthings.base

import androidx.lifecycle.ViewModel
import bry1337.github.io.newsthings.injection.component.DaggerViewModelInjector
import bry1337.github.io.newsthings.injection.component.ViewModelInjector
import bry1337.github.io.newsthings.injection.module.NetworkModule
import bry1337.github.io.newsthings.ui.detail.DetailViewModel
import bry1337.github.io.newsthings.ui.home.HomeViewModel

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
abstract class BaseViewModel : ViewModel() {
  private val injector: ViewModelInjector = DaggerViewModelInjector.builder().networkModule(NetworkModule).build()

  init {
    inject()
  }

  private fun inject() {
    when (this) {
      is HomeViewModel -> injector.inject(this)

      is DetailViewModel -> injector.inject(this)
    }

  }
}