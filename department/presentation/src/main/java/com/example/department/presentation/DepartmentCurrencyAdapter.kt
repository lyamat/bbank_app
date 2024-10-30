package com.example.department.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.department.DepartmentCurrency
import com.example.department.presentation.databinding.ItemDepartmentCurrencyRvBinding
import com.google.android.material.imageview.ShapeableImageView

internal class DepartmentCurrencyAdapter(
    val departmentCurrencies: List<DepartmentCurrency>
) : RecyclerView.Adapter<DepartmentCurrencyAdapter.DepartmentCurrencyAdapterViewHolder>() {

    internal inner class DepartmentCurrencyAdapterViewHolder(binding: ItemDepartmentCurrencyRvBinding) :
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
    ): DepartmentCurrencyAdapterViewHolder {
        val binding =
            ItemDepartmentCurrencyRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DepartmentCurrencyAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentCurrencyAdapterViewHolder, position: Int) {
        val currency = departmentCurrencies[position]
        with(holder) {
            tvCurrencyCode.text = currency.currency.name
            tvCurrencyBuyRate.text = currency.rateIn
            tvCurrencySaleRate.text = currency.rateIn
            tvCurrencyExchangeDescription.text = currency.scaleDescription
            ivCurrencyImage.setImageResource(
                setResourceId(
                    holder,
                    currency.currency.name.lowercase()
                )
            )
        }
    }

    private fun setResourceId(
        holder: DepartmentCurrencyAdapterViewHolder,
        currencyCode: String
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_$currencyCode",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )

    override fun getItemCount(): Int = departmentCurrencies.size
}