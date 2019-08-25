package bry1337.github.io.newsthings.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import bry1337.github.io.newsthings.R
import bry1337.github.io.newsthings.databinding.ItemNewsBinding
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.util.OnBindViewListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
typealias OnNewsClickListener = (Article) -> Unit

class TopNewsAdapter : RecyclerView.Adapter<TopNewsAdapter.ViewHolder>() {

  private lateinit var newsList: ArrayList<Article>
  private lateinit var listener: OnNewsClickListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding: ItemNewsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_news,
        parent, false)
    return ViewHolder(binding)
  }

  override fun getItemCount(): Int {
    return if (::newsList.isInitialized) newsList.size else 0
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.onBind(newsList[position])
    holder.itemView.setOnClickListener { listener(newsList[position]) }
  }

  fun updateNewsList(newsList: ArrayList<Article>) {
    this.newsList = newsList
    notifyDataSetChanged()
  }

  fun setListener(onNewsClickListener: OnNewsClickListener){
    this.listener = onNewsClickListener
  }

  inner class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(
      binding.root), OnBindViewListener<Article> {

    private val viewModel = TopNewsViewModel()
    private val ivNews = binding.root.ivNewsImage

    override fun onBind(item: Article) {
      viewModel.bind(item)
      binding.viewModel = viewModel
      Glide.with(binding.root).load(item.urlToImage).apply(RequestOptions().centerCrop()).into(ivNews)
    }
  }
}