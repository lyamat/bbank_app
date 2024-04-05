package com.example.bbank.presentation.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.CityRvItemBinding
import com.example.bbank.domain.models.Cities
import com.example.bbank.presentation.exchanges.ExchangesViewModel

internal class CityAdapter(
    private val cities: List<Cities>,
    private val exchangesViewModel: ExchangesViewModel
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var lastChecked: RadioButton? = null
    private var lastCheckedPos = 0

    inner class CityViewHolder(private val binding: CityRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                lastChecked?.apply { isChecked = false }
                lastChecked = binding.rbChosenCity
                lastCheckedPos = adapterPosition
                binding.rbChosenCity.isChecked = true
                exchangesViewModel.updateSelectedCity(cities[adapterPosition].cityName)
            }
        }

        fun bind(cities: Cities, position: Int) {
            binding.tvCity.text = cities.cityName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cities[position], position)
    }

    override fun getItemCount() = cities.size
}












