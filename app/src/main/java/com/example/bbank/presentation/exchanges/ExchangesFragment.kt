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
import com.example.bbank.presentation.adapters.ExchangesAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
internal class ExchangesFragment : Fragment() {
    private lateinit var binding: FragmentExchangesBinding
    private val exchangesViewModel by activityViewModels<ExchangesViewModel>()

//    @Inject
//    lateinit var sharedPreferences: SharedPreferences

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
        setupRecyclerViews()
    }

    private fun onStartExchangesFragment() {
        exchangesViewModel.getCurrentCity()

        binding.apply {
            btnGetRemoteExchanges.setOnClickListener {
                exchangesViewModel.getRemoteExchangesByCity("") // TODO: in future make with city?
//                exchangesViewModel.getRemoteExchangesByCity(sharedPreferences.getString("currentCity", "") ?: "")
            }
            btnGetLocalExchanges.setOnClickListener {
                exchangesViewModel.getLocalExchangesByCity()
            }

            chipCity.setOnClickListener {
                openCitySelectionDetailDialog()
            }

        }
//        (activity as MainActivity).supportActionBar?.apply {
//            title = "Exchanges"
//        }
    }

    private fun setupRecyclerViews() {
        binding.rvExchanges.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = ExchangesAdapter(
                context = requireContext(),
                exchanges = emptyList(),
                onClick = { } // TODO: add DepartmentDetailFragment
            )
            val divider =
                MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }
    }

    private fun openCitySelectionDetailDialog() {
        CitySelectionDialog.display(getParentFragmentManager(), requireContext())
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
            is ExchangesViewModel.ExchangesEvent.ExchangesSuccess -> {
                handleSuccess(exchangesEvent.exchanges)
                hideLoading()
            }

            is ExchangesViewModel.ExchangesEvent.Loading -> {
                showLoading()
            }

            is ExchangesViewModel.ExchangesEvent.Error -> {
                handleError(exchangesEvent.message)
                hideLoading()
            }

            is ExchangesViewModel.ExchangesEvent.CitySuccess -> {
                handleCitySuccess(exchangesEvent.cityName)
                hideLoading()
            }

            else -> Unit
        }
    }

    private fun handleCitySuccess(cityName: String) {
        binding.chipCity.text = cityName
    }

    private fun handleSuccess(exchanges: List<Exchanges>) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
        val currentCity = exchanges[0].name

        binding.apply {
            chipCity.text = currentCity
            tvExchangesCity.text = "Отделения в $currentCity\n$currentTime"
            (rvExchanges.adapter as ExchangesAdapter).updateExchanges(
                exchanges/*.shuffled().take(20)*/ // TODO: add pagination?
            )
        }
    }

    private fun handleError(error: String) {
        Snackbar.make(requireView(), error, Snackbar.LENGTH_SHORT)
            .setAnchorView(R.id.bottomNavigation)
            .show()
    }

    private fun hideLoading() {
        binding.progressIndicatorExchanges.visibility = View.GONE
        binding.btnGetRemoteExchanges.visibility = View.VISIBLE
        binding.btnGetLocalExchanges.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressIndicatorExchanges.visibility = View.VISIBLE
        binding.btnGetRemoteExchanges.visibility = View.INVISIBLE
        binding.btnGetLocalExchanges.visibility = View.INVISIBLE
    }
}