package bry1337.github.io.newsthings.injection.module

import bry1337.github.io.newsthings.BuildConfig
import bry1337.github.io.newsthings.network.NewsApi
import bry1337.github.io.newsthings.network.SelfSigningClientBuilder
import bry1337.github.io.newsthings.util.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Module
object NetworkModule {

  @Provides
  @Reusable
  @JvmStatic
  internal fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
  }

  @Provides
  @Reusable
  @JvmStatic
  internal fun provideRetrofitInstance(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(SelfSigningClientBuilder.unsafeOkHttpClient.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).build()
  }
}