package com.example.bbank.presentation.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.CurrencyRvItemBinding
import com.example.bbank.domain.models.Currency
import javax.inject.Inject

internal class CurrencyAdapter @Inject constructor(
    private val currencies: List<Currency>,
    private val onCurrencyValueChanged: (String, Double) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(
        binding: CurrencyRvItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            CurrencyRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]


        setCurrencyValueListener(holder.etCurrencyValue, position)
    }

    private fun setCurrencyValueListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newValue = s.toString().toDoubleOrNull()
                if (newValue != null) {
                    onCurrencyValueChanged(currencies[position].currencyCode, newValue)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun getItemCount() = currencies.size
}