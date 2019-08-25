package bry1337.github.io.newsthings.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.model.dao.ArticleDao

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Database(entities = [(Article::class)], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
  abstract fun articleDao(): ArticleDao
}