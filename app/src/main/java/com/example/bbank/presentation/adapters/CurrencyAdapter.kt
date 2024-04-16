package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.databinding.CurrencyRvItemBinding
import javax.inject.Inject

internal class CurrencyAdapter @Inject constructor(
    private var currencyRates: List<Pair<String, String>>,
    private val onCurrencyValueChanged: (List<Pair<String, String>>, String, String) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var focusedPosition = -1
    private var focusedView: View? = null

    inner class CurrencyViewHolder(
        binding: CurrencyRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CurrencyViewHolder {
        val binding =
            CurrencyRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyRates[position]

        holder.etCurrencyValue.setText(currency.second)
        holder.ivCurrency.setImageResource(setResourceId(holder, currency))

        setSelectionForEditText(position)
        setCurrencyValueTextChangedListener(holder.etCurrencyValue, position)
        setCurrencyValueOnFocusChangeListener(holder.etCurrencyValue, position)
    }

    private fun setSelectionForEditText(position: Int) {
        if (position == focusedPosition) {
            focusedView?.requestFocus()
            if (focusedView is EditText) {
                (focusedView as EditText).setSelection((focusedView as EditText).text.length)
            }
        }
    }

    private fun setResourceId(holder: CurrencyViewHolder, currency: Pair<String, String>): Int =
        holder.ivCurrency.context.resources.getIdentifier(
            "ic_${currency.first}", "drawable", holder.ivCurrency.context.packageName
        )

    private fun setCurrencyValueOnFocusChangeListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                focusedPosition = position
                focusedView = v
            }
        }
    }

    private fun setCurrencyValueTextChangedListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.doAfterTextChanged {
            if (etCurrencyValue.hasFocus()) {
                val newValue = it.toString()
                onCurrencyValueChanged(currencyRates, currencyRates[position].first, newValue)
            }
        }
    }

    override fun getItemCount() = currencyRates.size

    fun updateCurrencyRates(newCurrencyRates: List<Pair<String, String>>) {
        currencyRates = newCurrencyRates
        notifyDataSetChanged()
    }
}