package bry1337.github.io.newsthings.ui.home

import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import bry1337.github.io.newsthings.model.database.AppDatabase
import bry1337.github.io.newsthings.network.NewsApi
import bry1337.github.io.newsthings.util.Constants
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.amshove.kluent.`should be`
import org.amshove.kluent.mock
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import androidx.room.Room
import bry1337.github.io.newsthings.model.dao.ArticleDao
import org.junit.After
import java.io.IOException


/**
 * Created by edwardbryan.abergas on 08/25/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
  /**
   * Add rule for running LiveData
   */
  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  /**
   * Declaring mock observer
   */
  private val observer: Observer<String> = mock()
  private lateinit var viewModel: HomeViewModel
  private lateinit var retrofit: Retrofit
  private lateinit var db: AppDatabase
  private lateinit var dao: ArticleDao

  /**
   * Setup Retrofit, ViewModel, OkHttpClient and setting observer for errorMessage field from viewModel
   *
   * Mock database to create a mockable instance for testing
   */
  @Throws(Exception::class)
  @Before
  fun setup() {
    val context = mock(Context::class)
    db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java!!).build()
    dao = db.articleDao()
    viewModel = HomeViewModel(dao)
    val client = OkHttpClient().newBuilder()
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    client.addInterceptor(interceptor)
    retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).client(client.build()).build()

    viewModel.errorMessage.observeForever(observer)
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    db.close()
  }


  /**
   * Running testing on API call and checking each value if it's
   * correctly being processed as we want them to be, and checking if the result
   * is non-null
   */
  @Test
  fun getApi() {
    retrofit.create(NewsApi::class.java)
        .getTopNews()
        .subscribeOn(Schedulers.trampoline())
        .doOnSubscribe {
          viewModel.loadingVisibility.value = View.VISIBLE
          viewModel.loadingVisibility.value.shouldNotBeNull()
          viewModel.loadingVisibility.value.shouldBe(View.VISIBLE)
        }.doOnTerminate {
          viewModel.loadingVisibility.value = View.GONE
          viewModel.loadingVisibility.value.shouldNotBeNull()
          viewModel.loadingVisibility.value.shouldBe(View.GONE)
        }
        .observeOn(Schedulers.trampoline())
        .subscribe({ result ->
          result.shouldNotBeNull()
          result.`should be`(expected = result)
        }, { throwable ->
          println(throwable.localizedMessage)
        })
  }

  /**
   * Testing mock observer to see if our observer function
   * can be tested in a way that a LiveData field would change accordingly and the changes
   * would be what we expect it to be.
   */
  @Test
  fun mockObserver() {
    val expectedValue = "No such method"
    viewModel.errorMessage.value = expectedValue

    val captor = ArgumentCaptor.forClass(String::class.java)
    captor.run {
      verify(observer, times(1)).onChanged(capture())
      assertEquals(expectedValue, value)
    }
    println(expectedValue)
  }
}