package bry1337.github.io.newsthings.util

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
fun View.getParentActivity(): AppCompatActivity? {
  var context = this.context
  while (context is ContextWrapper) {
    if (context is AppCompatActivity) {
      return context
    }
    context = context.baseContext
  }
  return null
}