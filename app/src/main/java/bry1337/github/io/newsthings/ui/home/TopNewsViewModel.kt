package bry1337.github.io.newsthings.ui.home

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.newsthings.base.BaseViewModel
import bry1337.github.io.newsthings.model.Article
import com.google.gson.annotations.SerializedName

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */

class TopNewsViewModel : BaseViewModel() {

  private val author = MutableLiveData<String>()
  private val title = MutableLiveData<String>()
  private val description = MutableLiveData<String>()
  private val url = MutableLiveData<String>()
  private val urlToImage = MutableLiveData<String>()
  private val publishedAt = MutableLiveData<String>()
  private val content = MutableLiveData<String>()

//  description title source

  fun bind(article: Article) {
    description.value = article.description
    title.value = article.title
  }

  fun getDescription(): MutableLiveData<String> {
    return description
  }

  fun getTitle(): MutableLiveData<String> {
    return title
  }
}