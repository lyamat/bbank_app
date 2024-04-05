package com.example.bbank.presentation.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentExchangesBinding
import com.example.bbank.domain.models.Exchanges
import com.example.bbank.presentation.adapters.CriteriaAdapter
import com.example.bbank.presentation.utils.CriteriaItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
internal class ExchangesFragment : Fragment() {
    private lateinit var binding: FragmentExchangesBinding
    private val exchangesViewModel by activityViewModels<ExchangesViewModel>()

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
        setupCriteriaRecyclerView()
    }

    private fun onStartExchangesFragment() {
        binding.apply {
            btnGetExchanges.setOnClickListener {
                exchangesViewModel.uploadRemoteExchanges()
            }
        }
    }

    private fun setupCriteriaRecyclerView() {
        binding.rvCriteria.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = CriteriaAdapter(
                items = listOf(CriteriaItem.CityItem(""), CriteriaItem.OnWorkItem(false)),
                onCityClick = { openCitySelectionDetailDialog() }
            )
        }
    }

    private fun openCitySelectionDetailDialog() {
        CitySelectionDialog.display(getParentFragmentManager())
    }

    private fun observeExchangesEvent() {
        CoroutineScope(Dispatchers.Main).launch {
            exchangesViewModel.exchangesFlow().collect {
                processExchangeEvent(it)
            }
        }
    }

    private fun processExchangeEvent(exchangesEvent: ExchangesViewModel.ExchangesEvent) {
        when (exchangesEvent) {
            is ExchangesViewModel.ExchangesEvent.Success -> {
                handleSuccess(exchangesEvent.exchanges)
                hideLoading()
            }

            is ExchangesViewModel.ExchangesEvent.CityUpdate -> {
                handleCityUpdate(exchangesEvent.city)
            }

            is ExchangesViewModel.ExchangesEvent.Loading -> {
                showLoading()
            }

            is ExchangesViewModel.ExchangesEvent.Error -> {
                handleError(exchangesEvent.message)
                hideLoading()
            }

            else -> Unit
        }
    }

    private fun handleCityUpdate(city: String) {
        (binding.rvCriteria.adapter as CriteriaAdapter).updateCriteriaAdapterData(
            CriteriaItem.CityItem(
                city
            )
        )
    }

    private fun handleSuccess(exchanges: List<Exchanges>) {
        binding.apply {
            tvResultOutput.text = exchanges[(0..exchanges.size).random()].street
        }
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun hideLoading() {
        binding.progressIndicatorExchanges.visibility = View.GONE
        binding.btnGetExchanges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressIndicatorExchanges.visibility = View.VISIBLE
        binding.btnGetExchanges.visibility = View.INVISIBLE
    }
}