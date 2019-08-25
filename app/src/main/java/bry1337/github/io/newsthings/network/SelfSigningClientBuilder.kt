package bry1337.github.io.newsthings.network

import bry1337.github.io.newsthings.BuildConfig
import java.security.cert.CertificateException
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by edwardbryan.abergas on 08/25/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
object SelfSigningClientBuilder {

  // Create a trust manager that does not validate certificate chains
  // Install the all-trusting trust manager
  // Create an ssl socket factory with our all-trusting manager
  val unsafeOkHttpClient: OkHttpClient.Builder
    get() {
      try {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
          @Throws(CertificateException::class)
          override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
          }

          @Throws(CertificateException::class)
          override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
          }

          override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
            return arrayOf()
          }
        })
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
          val interceptor = HttpLoggingInterceptor()
          interceptor.level = HttpLoggingInterceptor.Level.BODY
          builder.addInterceptor(interceptor)
        }
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }
        return builder
      } catch (e: Exception) {
        throw RuntimeException(e)
      }

    }
}