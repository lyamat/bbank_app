package com.example.bbank.presentation.departments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.ItemDepartmentCurrencyRateRvBinding
import com.example.bbank.presentation.base_utils.PresentationUtils.toCurrencyRatesToByn
import com.example.core.domain.department.Department
import com.google.android.material.imageview.ShapeableImageView

internal class DepartmentCurrencyRatesAdapter(
    department: Department,
) : RecyclerView.Adapter<DepartmentCurrencyRatesAdapter.DepartmentCurrencyRatesViewHolder>() {

    // TODO: remake this shit.. 
    private val currencyRatesToByn: List<Triple<String, Pair<String, String>, String>> =
        department.toCurrencyRatesToByn()

    internal inner class DepartmentCurrencyRatesViewHolder(binding: ItemDepartmentCurrencyRateRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val tvCurrencyExchangeDescription: TextView =
            binding.tvCurrencyExchangeDescription
        val tvCurrencyBuyRate: TextView = binding.tvCurrencyBuyRate
        val tvCurrencySaleRate: TextView = binding.tvCurrencySaleRate
        val ivCurrencyImage: ShapeableImageView = binding.ivCurrencyImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DepartmentCurrencyRatesViewHolder {
        val binding =
            ItemDepartmentCurrencyRateRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DepartmentCurrencyRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentCurrencyRatesViewHolder, position: Int) {
        val currencyRate = currencyRatesToByn[position]
        with(holder) {
            tvCurrencyCode.text = currencyRate.first
            tvCurrencyBuyRate.text = currencyRate.second.first
            tvCurrencySaleRate.text = currencyRate.second.second
            tvCurrencyExchangeDescription.text = currencyRate.third
            ivCurrencyImage.setImageResource(setResourceId(holder, currencyRate))
        }
    }

    private fun setResourceId(
        holder: DepartmentCurrencyRatesViewHolder,
        currencyRate: Triple<String, Pair<String, String>, String>
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_${currencyRate.first.lowercase()}",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )

    override fun getItemCount(): Int = currencyRatesToByn.size
}