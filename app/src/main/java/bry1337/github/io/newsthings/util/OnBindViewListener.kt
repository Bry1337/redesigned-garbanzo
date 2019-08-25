package bry1337.github.io.newsthings.util

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
interface OnBindViewListener<T> {
  fun onBind(item: T)
}