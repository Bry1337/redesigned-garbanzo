package bry1337.github.io.newsthings.model.response

import bry1337.github.io.newsthings.model.Article
import com.google.gson.annotations.SerializedName

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
data class TopHeadlineResponse(
    @SerializedName("articles")
    val articleList: ArrayList<Article>) {
}