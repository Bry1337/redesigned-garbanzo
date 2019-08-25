package bry1337.github.io.newsthings.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import bry1337.github.io.newsthings.databinding.FragmentHomeBinding
import bry1337.github.io.newsthings.injection.ViewModelFactory
import bry1337.github.io.newsthings.model.Article
import bry1337.github.io.newsthings.ui.MainActivity

/**
 * Created by edwardbryan.abergas on 08/24/2019.
 *
 * @author edwardbryan.abergas@gmail.com
 */
class HomeFragment : Fragment() {

  private val onNewsClickListener: OnNewsClickListener = this::onNewsClicked
  private lateinit var viewModel: HomeViewModel
  private lateinit var binding: FragmentHomeBinding
  private lateinit var activity: MainActivity

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(activity, ViewModelFactory(activity)).get(HomeViewModel::class.java)
    binding.viewModel = viewModel
    viewModel.setAdapterListener(onNewsClickListener)
  }

  override fun onResume() {
    super.onResume()
    viewModel.getTopNews()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    activity = context as MainActivity
  }

  private fun onNewsClicked(article: Article) {
    val navDirections = HomeFragmentDirections.actionToDetail(article)
    view?.let { findNavController().navigate(navDirections) }
  }
}