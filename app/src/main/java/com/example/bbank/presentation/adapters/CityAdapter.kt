package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.ItemCityRvBinding
import com.example.core.presentation.ui.util.checked

internal class CityAdapter(
    private val cities: List<String>,
    private val currentCity: String?,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    internal inner class CityViewHolder(binding: ItemCityRvBinding) :
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
            tvCity.text = city
            if (city == currentCity) rbChosenCity.checked()
            cityCardView.setOnClickListener { onClick(city) }
        }
    }

    override fun getItemCount() = cities.size
}