package bry1337.github.io.newsthings.ui.detail

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.newsthings.base.BaseViewModel
import bry1337.github.io.newsthings.model.Article

/**
 * Created by edwardbryan.abergas on 08/25/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class DetailViewModel : BaseViewModel() {

  val author: MutableLiveData<String> = MutableLiveData()
  val title: MutableLiveData<String> = MutableLiveData()
  val publishedAt: MutableLiveData<String> = MutableLiveData()
  val description: MutableLiveData<String> = MutableLiveData()

  fun setValues(article: Article?) {
    author.value = article?.author
    title.value = article?.title
    publishedAt.value = article?.publishedAt
    description.value = article?.description
  }
}