package bry1337.github.io.newsthings.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import bry1337.github.io.newsthings.databinding.FragmentDetailBinding
import bry1337.github.io.newsthings.injection.ViewModelFactory
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.ui.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Created by edwardbryan.abergas on 08/25/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class DetailFragment : Fragment() {

  private lateinit var binding: FragmentDetailBinding
  private lateinit var viewModel: DetailViewModel
  private lateinit var activity: MainActivity
  private var article: Article? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(activity, ViewModelFactory(activity)).get(DetailViewModel::class.java)
    binding.viewModel = viewModel
    article = arguments?.let { DetailFragmentArgs.fromBundle(it).article }
    viewModel.setValues(article)
    Glide.with(activity).load(article?.urlToImage).apply(RequestOptions().centerCrop()).into(ivNewsImage)
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activity = context as MainActivity
  }
}