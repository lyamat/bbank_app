package com.example.bbank.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bbank.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNews()
    }

    private fun observeNews() {
        newsViewModel.news.observe(viewLifecycleOwner) { news ->
            binding.apply {
                tvResultOutput.text  = news[0].nameRu
            }
        }

        newsViewModel.localNewsSize.observe(viewLifecycleOwner) { localNewsSize ->
            binding.apply {
                tvTemp.text  = localNewsSize.toString()
            }
        }
    }
}