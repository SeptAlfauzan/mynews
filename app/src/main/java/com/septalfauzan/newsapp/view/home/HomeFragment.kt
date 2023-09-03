package com.septalfauzan.newsapp.view.home

import android.graphics.Path.Direction
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.septalfauzan.newsapp.R
import com.septalfauzan.newsapp.core.data.Resource
//import androidx.lifecycle.lifecycleScope
import com.septalfauzan.newsapp.core.presentation.HomeViewModel
import com.septalfauzan.newsapp.core.ui.HeadLineAdapter
import com.septalfauzan.newsapp.core.ui.NewsAdapter
import com.septalfauzan.newsapp.core.ui.ViewModelFactory
import com.septalfauzan.newsapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity == null) return
        val factory = ViewModelFactory.getInstance(requireContext())
        val homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]


        val newsAdapter = NewsAdapter(onClick = { url ->
            val bundle = bundleOf("url" to url)
            findNavController().navigate(R.id.action_homeFragment_to_detailNewsFragment, bundle)
        })
        val headlineAdapter = HeadLineAdapter(onClick = { url ->
            val bundle = bundleOf("url" to url)
            findNavController().navigate(R.id.action_homeFragment_to_detailNewsFragment, bundle)
        })

        binding.newsRv.initNewsRecyclerView(newsAdapter)
        binding.headlineRv.initHeadLineRecyclerView(headlineAdapter)

        homeViewModel.headLines.observe(viewLifecycleOwner) { headline ->
            if (headline == null) return@observe
            when (headline) {
                is Resource.Loading -> {
                    binding.headlineShimmer.shimmerLayout.startShimmer()
                    binding.headlineShimmer.shimmerLayout.visibility = View.VISIBLE
                    homeViewModel.getHeadlines()
                }
                is Resource.Error -> {
                    handleHeadlineShowError(true, errorMessage = headline.message.toString())
                }
                is Resource.Success -> {
                    headlineAdapter.setData(headline.data)
                    binding.headlineShimmer.shimmerLayout.stopShimmer()
                    binding.headlineShimmer.shimmerLayout.visibility = View.GONE
                    handleHeadlineShowError(false)
                }
            }
        }

        homeViewModel.getNewsPaging().observe(viewLifecycleOwner) {
            binding.newsShimmer.shimmerLayout.startShimmer()
            binding.newsShimmer.shimmerLayout.visibility = View.VISIBLE

            newsAdapter.submitData(lifecycle, it)

            binding.newsShimmer.shimmerLayout.stopShimmer()
            binding.newsShimmer.shimmerLayout.visibility = View.GONE
            handleNewsShowError(false)
        }
    }

    private fun RecyclerView.initNewsRecyclerView(adapter: NewsAdapter) {
        this.adapter = adapter
    }

    private fun RecyclerView.initHeadLineRecyclerView(adapter: HeadLineAdapter) {
        this.adapter = adapter
    }

    private fun handleNewsShowError(status: Boolean, errorMessage: String = "") {
        if (status) {
            binding.errorView.visibility = View.VISIBLE
            binding.newsRv.visibility = View.INVISIBLE
            binding.errorTextView.text = errorMessage
        } else {
            binding.errorView.visibility = View.INVISIBLE
            binding.newsRv.visibility = View.VISIBLE
        }
    }

    private fun handleHeadlineShowError(status: Boolean, errorMessage: String = "") {
        if (status) {
            binding.headlineRv.visibility = View.INVISIBLE
        } else {
            binding.headlineRv.visibility = View.VISIBLE
        }
    }
}