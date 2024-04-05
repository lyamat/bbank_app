package com.example.bbank.presentation.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bbank.R
import com.example.bbank.databinding.FragmentExchangesBinding
import com.example.bbank.domain.models.Exchanges
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
internal class ExchangesFragment : Fragment() {
    private lateinit var binding: FragmentExchangesBinding
    private val exchangesViewModel: ExchangesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
//                showDialog()
//                openDialog()
                exchangesViewModel.uploadRemoteExchanges()
            }
        }
    }

    private fun openDialog() {
//        NewsDetailDialog.display(getParentFragmentManager())
    }

//    private fun showDialog() {
//        val dialogFragment = FullScreenDialogExample()
//        dialogFragment.show(childFragmentManager, "signature")
//    }

    private fun hideLoading() {
        binding.progressIndicatorExchanges.visibility = View.GONE
        binding.btnGetExchanges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressIndicatorExchanges.visibility = View.VISIBLE
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

    private fun observeExchangesEvent() {
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