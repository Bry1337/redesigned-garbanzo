package bry1337.github.io.newsthings.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import bry1337.github.io.newsthings.model.database.AppDatabase
import bry1337.github.io.newsthings.ui.detail.DetailViewModel
import bry1337.github.io.newsthings.ui.home.HomeViewModel
import bry1337.github.io.newsthings.util.Constants
import java.lang.IllegalArgumentException

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
      val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, Constants.ARTICLE)
          .build()
      return HomeViewModel(db.articleDao()) as T
    } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
      return DetailViewModel() as T
    }
    throw IllegalArgumentException("Unknown ViewModel Class")
  }
}