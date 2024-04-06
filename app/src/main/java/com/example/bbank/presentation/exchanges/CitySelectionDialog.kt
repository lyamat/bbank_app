package com.example.bbank.presentation.exchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.CitySelectionDialogBinding
import com.example.bbank.domain.models.Cities
import com.example.bbank.presentation.adapters.CityAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class CitySelectionDialog : DialogFragment() {
    private lateinit var cities: List<Cities>
    private lateinit var binding: CitySelectionDialogBinding

    private val exchangesViewModel by activityViewModels<ExchangesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.AppTheme_Slide)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CitySelectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tbCitySelection.setNavigationOnClickListener { dismiss() }
            tbCitySelection.setTitle("City selection") // title from news object
            tbCitySelection.setOnMenuItemClickListener {
                dismiss()
                true
            }
            val adapter = CityAdapter(cities, exchangesViewModel) // Pass viewModel here
            rvCities.layoutManager = LinearLayoutManager(context)
            rvCities.adapter = adapter
        }
    }

    internal companion object {
        private const val TAG = "city_selection_dialog"
        fun display(fragmentManager: FragmentManager?): CitySelectionDialog {
            val citySelectionDialog = CitySelectionDialog()
            citySelectionDialog.cities = mutableListOf( // TODO: remake dynamically
                Cities("Minsk"),
                Cities("Brest"),
                Cities("Gomel")
            )
            citySelectionDialog.show(fragmentManager!!, TAG)
            return citySelectionDialog
        }
    }
}