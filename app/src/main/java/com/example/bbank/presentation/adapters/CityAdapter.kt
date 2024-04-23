package com.example.bbank.presentation.adapters

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.ItemCityRvBinding
import com.example.bbank.domain.models.City

internal class CityAdapter(
    private val cities: List<City>,
    private val sharedPreferences: SharedPreferences,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(binding: ItemCityRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCity: TextView = binding.tvCity
        val rbChosenCity: RadioButton = binding.rbChosenCity
        val cityCardView: CardView = binding.cityCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        with(holder) {
            tvCity.text = city.cityName
            rbChosenCity.isChecked = isChosenCityEqualsCurrentCity(city.cityName)
            setOnCityClick(cityCardView, position)
        }
    }

    private fun isChosenCityEqualsCurrentCity(cityName: String): Boolean =
        cityName == sharedPreferences.getString("currentCity", "")

    private fun setOnCityClick(cityCardView: CardView, position: Int) =
        cityCardView.setOnClickListener {
            onClick(cities[position].cityName)
        }

    override fun getItemCount() = cities.size
}