package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.ItemCurrencyRateRvBinding
import com.example.bbank.presentation.utils.PresentationUtils.toCurrencyRatesToByn
import com.example.core.domain.department.Department
import com.google.android.material.imageview.ShapeableImageView

internal class CurrencyRatesAdapter(
    department: Department,
) : RecyclerView.Adapter<CurrencyRatesAdapter.CurrencyRatesViewHolder>() {

    private val currencyRatesToByn: List<Triple<String, Pair<String, String>, String>> =
        department.toCurrencyRatesToByn()

    internal inner class CurrencyRatesViewHolder(binding: ItemCurrencyRateRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val tvCurrencyExchangeDescription: TextView =
            binding.tvCurrencyExchangeDescription // TODO: add (maybe already implemented, check it)
        val tvBuyRate: TextView = binding.tvBuyRate
        val tvSaleRate: TextView = binding.tvSaleRate
        val ivCurrencyImage: ShapeableImageView = binding.ivCurrencyImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRatesViewHolder {
        val binding =
            ItemCurrencyRateRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyRatesViewHolder, position: Int) {
        val currencyRate = currencyRatesToByn[position]
        with(holder) {
            tvCurrencyCode.text = currencyRate.first
            tvBuyRate.text = currencyRate.second.first
            tvSaleRate.text = currencyRate.second.second
            tvCurrencyExchangeDescription.text = currencyRate.third
            ivCurrencyImage.setImageResource(setResourceId(holder, currencyRate))
        }
    }

    private fun setResourceId(
        holder: CurrencyRatesViewHolder,
        currencyRate: Triple<String, Pair<String, String>, String>
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_${currencyRate.first.lowercase()}",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )

    override fun getItemCount(): Int = currencyRatesToByn.size
}