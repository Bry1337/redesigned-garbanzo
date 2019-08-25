package bry1337.github.io.newsthings.ui.home

import android.view.View
import androidx.lifecycle.MutableLiveData
import bry1337.github.io.newsthings.base.BaseViewModel
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.model.dao.ArticleDao
import bry1337.github.io.newsthings.model.response.TopHeadlineResponse
import bry1337.github.io.newsthings.network.NewsApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeViewModel(private val articleDao: ArticleDao) : BaseViewModel() {

  @Inject
  lateinit var api: NewsApi

  val topNewsAdapter: TopNewsAdapter = TopNewsAdapter()
  val errorMessage: MutableLiveData<String> = MutableLiveData()
  val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
  private lateinit var subscription: Disposable

  override fun onCleared() {
    super.onCleared()
    if (::subscription.isInitialized) {
      subscription.dispose()
    }
  }

  fun setAdapterListener(onNewsClickListener: OnNewsClickListener){
    topNewsAdapter.setListener(onNewsClickListener)
  }

  fun getTopNews() {
    subscription =
        api.getTopNews()
            .flatMap { result ->
              articleDao.insertArticleList(result.articleList)
              Observable.just(result.articleList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveNewsStarted() }
            .doOnTerminate { onRetrieveNewsTerminate() }
            .subscribe({result ->
              setTopNews(result)
            }, { throwable ->
              onError(throwable)
            })
  }

  private fun onError(throwable: Throwable) {
    errorMessage.value = throwable.localizedMessage
  }

  private fun onRetrieveNewsStarted() {
    loadingVisibility.value = View.VISIBLE
  }

  private fun onRetrieveNewsTerminate() {
    loadingVisibility.value = View.GONE
  }

  private fun setTopNews(newsList: ArrayList<Article>) {
    topNewsAdapter.updateNewsList(newsList)
  }
}