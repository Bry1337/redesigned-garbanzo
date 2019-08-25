package bry1337.github.io.newsthings.network

import bry1337.github.io.newsthings.model.response.TopHeadlineResponse
import bry1337.github.io.newsthings.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
interface NewsApi {

  @GET(Constants.PAYLOAD_FORMAT)
  fun getTopNews(): Observable<TopHeadlineResponse>

}