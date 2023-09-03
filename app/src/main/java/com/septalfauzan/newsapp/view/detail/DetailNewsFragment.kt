package com.septalfauzan.newsapp.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.septalfauzan.newsapp.R
import com.septalfauzan.newsapp.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var urlArgument: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        urlArgument = requireArguments().getString("url") ?: ""
        // Inflate the layout for this fragment
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webViewContainer = binding.webViewContainer

        Log.d("TAG", "onViewCreated: $urlArgument")
        webViewContainer.settings.javaScriptEnabled = true
        webViewContainer.settings.loadWithOverviewMode = true
        webViewContainer.settings.useWideViewPort = true
        webViewContainer.settings.domStorageEnabled = true
        webViewContainer.webViewClient = WebViewClient() // Add this line to handle links within the WebView

        webViewContainer.loadUrl(urlArgument)
    }
}