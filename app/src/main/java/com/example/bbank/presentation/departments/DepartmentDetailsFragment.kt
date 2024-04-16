package com.example.bbank.presentation.departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bbank.R
import com.example.bbank.databinding.FragmentDepartmentDetailsBinding
import com.example.bbank.presentation.adapters.CurrencyRatesAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
internal class DepartmentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentDepartmentDetailsBinding
    private val args by navArgs<DepartmentDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDepartmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCurrencyRatesRecyclerView()
        setupFragmentViews()
    }

    private fun setupFragmentViews() {
        binding.apply {
            val department = args.department
            val address = getString(
                R.string.department_address,
                department.nameType,
                department.name,
                department.streetType,
                department.street,
                department.homeNumber
            )
            tvDepartmentAddress.text = address
            tvDepartmentName.text = getString(R.string.oao_belarusbank, department.filialsText)
            tvUpdateTime.text = SimpleDateFormat("HH:mm", Locale.UK).format(Date())
        }
    }

    private fun setupCurrencyRatesRecyclerView() {
        binding.rvCurrencyBuySale.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CurrencyRatesAdapter(
                department = args.department
            )
        }
    }
}