package com.example.bbank.presentation.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bbank.R
import com.example.bbank.databinding.FragmentExchangesBinding
import com.example.bbank.domain.models.Exchanges
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExchangesFragment : Fragment() {
    private lateinit var binding: FragmentExchangesBinding
    private val exchangesViewModel: ExchangesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartExchangesFragment()
        observeExchangesEvent()
    }

    private fun onStartExchangesFragment() {
        binding.apply {
            btnGetExchanges.setOnClickListener {
                lifecycleScope.launch {
                    exchangesViewModel.uploadRemoteExchanges()
                }
            }
        }
    }

    private fun hideLoading() {
        binding.pbExchanges.visibility = View.INVISIBLE
        binding.btnGetExchanges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.pbExchanges.visibility = View.VISIBLE
        binding.btnGetExchanges.visibility = View.INVISIBLE
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun handleSuccess(exchanges: List<Exchanges>) {
        binding.apply {
            tvResultOutput.text = exchanges[(0..exchanges.size).random()].street
        }
    }

    private fun observeExchangesEvent() { // TODO: observer
        CoroutineScope(Dispatchers.Main).launch {
            exchangesViewModel.exchangesFlow().collect {
                when (it) {
                    is ExchangesViewModel.ExchangesEvent.Success -> {
                        handleSuccess(it.exchanges)
                        hideLoading()
                    }
                    is ExchangesViewModel.ExchangesEvent.Error -> {
                        handleError(it.message)
                        hideLoading()
                    }
                    is ExchangesViewModel.ExchangesEvent.Loading -> {
                        showLoading()
                    }
                    else -> Unit
                }
            }
        }
    }
}