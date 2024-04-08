package com.example.bbank.presentation.adapters


import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
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

    inner class CityViewHolder(binding: CityRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvCity: TextView = binding.tvCity
        val rbChosenCity: RadioButton = binding.rbChosenCity
        val cityCardView: CardView = binding.cityCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]

        setOnCityClick(holder.cityCardView, position)
        holder.tvCity.text = city.cityName
        holder.rbChosenCity.isChecked = isChosenCityEqualsCurrentCity(city.cityName)
    }

    private fun isChosenCityEqualsCurrentCity(cityName: String): Boolean {
        return cityName == sharedPreferences.getString("currentCity", "")
    }

    private fun setOnCityClick(cityCardView: CardView, position: Int) {
        cityCardView.setOnClickListener {
            val selectedCity = cities[position].cityName
            sharedPreferences.edit().putString("currentCity", selectedCity).apply()
            // TODO: remove vm from here!!!
            exchangesViewModel.getLocalExchangesByCity(cities[position].cityName)
            dialog.dismiss()
        }
    }

    override fun getItemCount() = cities.size
}