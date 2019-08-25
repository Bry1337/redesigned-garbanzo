package bry1337.github.io.newsthings.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bry1337.github.io.newsthings.model.Article
import io.reactivex.Completable

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Dao
interface ArticleDao {

  @get:Query("Select * from article")
  val all: List<Article>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertArticleList(articleList: List<Article>)

  @Query("Update article set isSaved = :isSaved where id = :id")
  fun saveArticle(isSaved: Boolean, id: Long)

  @Query("Update article set isArchived = :isArchived where id = :id")
  fun archiveArticle(isArchived: Boolean, id: Long)

}