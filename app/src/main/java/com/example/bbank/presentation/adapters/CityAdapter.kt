package com.example.bbank.presentation.adapters


import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.CityRvItemBinding
import com.example.bbank.domain.models.Cities
import com.example.bbank.presentation.exchanges.CitySelectionDialog
import com.example.bbank.presentation.exchanges.ExchangesViewModel
import javax.inject.Inject

internal class CityAdapter @Inject constructor(
    private val cities: List<Cities>,
    private val exchangesViewModel: ExchangesViewModel,
    private val sharedPreferences: SharedPreferences,
    private val dialog: CitySelectionDialog
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(private val binding: CityRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.cvCityItem.setOnClickListener {
                val selectedCity = binding.tvCity.text.toString()
                sharedPreferences.edit().putString("currentCity", selectedCity).apply()
                // TODO: choose (remote vs local)
//                exchangesViewModel.getRemoteExchangesByCity(cities[adapterPosition].cityName)
                exchangesViewModel.getLocalExchangesByCity(cities[adapterPosition].cityName)

                dialog.dismiss()
            }
        }

        fun bind(city: Cities) {
            binding.tvCity.text = city.cityName
            binding.rbChosenCity.isChecked =
                city.cityName == sharedPreferences.getString("currentCity", "")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount() = cities.size
}