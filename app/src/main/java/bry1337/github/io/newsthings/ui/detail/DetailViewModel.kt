package bry1337.github.io.newsthings.ui.detail

import androidx.lifecycle.MutableLiveData
import bry1337.github.io.newsthings.R
import bry1337.github.io.newsthings.base.BaseViewModel
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.model.dao.ArticleDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by edwardbryan.abergas on 08/25/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class DetailViewModel(private val articleDao: ArticleDao) : BaseViewModel() {

  val author: MutableLiveData<String> = MutableLiveData()
  val title: MutableLiveData<String> = MutableLiveData()
  val publishedAt: MutableLiveData<String> = MutableLiveData()
  val description: MutableLiveData<String> = MutableLiveData()
  val successMessage: MutableLiveData<Int> = MutableLiveData()
  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    if (::subscription.isInitialized) {
      subscription.dispose()
    }
  }

  fun setValues(article: Article?) {
    author.value = article?.author
    title.value = article?.title
    publishedAt.value = article?.publishedAt
    description.value = article?.description
  }

  fun saveToArticle(article: Article) {
    subscription = Observable.fromCallable {
      articleDao.saveArticle(true, article.id)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnTerminate { setNullValue() }
        .subscribe({
          saveArticleSucceed()
        }, {

        })

  }

  fun archiveArticle(article: Article) {
    subscription = Observable.fromCallable {
      articleDao.archiveArticle(true, article.id)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnTerminate { setNullValue() }
        .subscribe({
          archiveArticleSucceed()
        }, {

        })
  }

  private fun archiveArticleSucceed() {
    successMessage.value = R.string.archive_article_succeed
  }

  private fun saveArticleSucceed() {
    successMessage.value = R.string.successfully_saved
  }

  private fun setNullValue() {
    successMessage.value = null
  }
}