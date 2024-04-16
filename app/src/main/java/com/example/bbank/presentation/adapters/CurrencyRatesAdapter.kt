package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.CurrencyRateRvItemBinding
import com.example.bbank.domain.models.Department
import com.example.bbank.domain.models.toCurrencyRatesToByn
import com.google.android.material.imageview.ShapeableImageView

internal class CurrencyRatesAdapter(
    department: Department,
) : RecyclerView.Adapter<CurrencyRatesAdapter.CurrencyRatesViewHolder>() {

    private val currencyRatesToByn: List<Pair<String, Pair<String, String>>> =
        department.toCurrencyRatesToByn()

    inner class CurrencyRatesViewHolder(binding: CurrencyRateRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val tvCurrencyExchangeCount: TextView = binding.tvCurrencyExchangeCount
        val tvBuyRate: TextView = binding.tvBuyRate
        val tvSaleRate: TextView = binding.tvSaleRate
        val ivCurrencyImage: ShapeableImageView = binding.ivCurrencyImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRatesViewHolder {
        val binding =
            CurrencyRateRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyRatesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currencyRatesToByn.size
    }

    override fun onBindViewHolder(holder: CurrencyRatesViewHolder, position: Int) {
        val currencyRate = currencyRatesToByn[position]

        holder.ivCurrencyImage.setImageResource(setResourceId(holder, currencyRate))

        holder.tvCurrencyCode.text = currencyRate.first
        holder.tvBuyRate.text = currencyRate.second.first
        holder.tvSaleRate.text = currencyRate.second.second
    }

    private fun setResourceId(
        holder: CurrencyRatesViewHolder,
        currencyRate: Pair<String, Pair<String, String>>
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_${currencyRate.first.lowercase()}",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )
}