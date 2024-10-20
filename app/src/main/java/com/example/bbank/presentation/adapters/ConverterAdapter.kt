package com.example.bbank.presentation.adapters

import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemCurrencyRvBinding
import com.example.bbank.presentation.converter.ConverterEvent
import com.example.bbank.presentation.utils.DecimalDigitsInputFilter
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class ConverterAdapter(
    private var currencyValues: List<Pair<String, String>>,
    private val onConverterEvent: (ConverterEvent) -> Unit
) : RecyclerView.Adapter<ConverterAdapter.ConverterViewHolder>() {

    private var savedPosition = -1

    inner class ConverterViewHolder(binding: ItemCurrencyRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val cvCurrency: MaterialCardView = binding.cvCurrency
        val btnClear: ImageView = binding.btnClear
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterViewHolder {
        val binding =
            ItemCurrencyRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConverterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterViewHolder, position: Int) {
        val currency = currencyValues[position]
        with(holder) {
            setIsRecyclable(false)
            etCurrencyValue.setText(currency.second)
            tvCurrencyCode.text = currency.first.uppercase(Locale.ROOT)
            etCurrencyValue.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
            ivCurrency.setImageResource(setResourceId(holder, currency))
            setupItem(holder, position)
            setCurrencyValueTextChangedListener(etCurrencyValue, position)
            setCurrencyValueOnFocusChangeListener(holder)
            setBtnClearOnClickListener(holder.btnClear, position)
        }
    }

    private fun setupItem(holder: ConverterViewHolder, position: Int) {
        with(holder) {
            if (position == savedPosition) {
                etCurrencyValue.requestFocus()
                etCurrencyValue.setSelection(etCurrencyValue.text.length)
                setCurrencyViewAppearance(holder, R.color.lime_green, 6, View.VISIBLE)
            } else {
                setCurrencyViewAppearance(holder, R.color.gray, 1, View.GONE)
            }
        }
    }

    private fun setCurrencyValueTextChangedListener(etCurrencyValue: EditText, position: Int) {
        etCurrencyValue.doAfterTextChanged { it ->
            if (it.toString().isEmpty()) {
                onConverterEvent(ConverterEvent.ClearAllValues)
            } else if (it.toString() != ".") {
                val newValue = when {
                    it.toString() == "0" -> "0"
                    it.toString() == "0." -> "0."
                    it.toString().startsWith("0.") -> it.toString()
                    it.toString().startsWith("0") -> it.toString().substring(1)
                    else -> it.toString()
                }
                onConverterEvent(
                    ConverterEvent.ValuesChanged(
                        currencyValues[position].first,
                        newValue
                    )
                )
                savedPosition = position
            }
        }
    }

    private fun setCurrencyValueOnFocusChangeListener(holder: ConverterViewHolder) {
        with(holder) {
            etCurrencyValue.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setCurrencyViewAppearance(this, R.color.lime_green, 6, View.VISIBLE)
                } else {
                    setCurrencyViewAppearance(this, R.color.gray, 1, View.GONE)
                }
            }
        }
    }

    private fun setCurrencyViewAppearance(
        holder: ConverterViewHolder,
        strokeColor: Int,
        strokeWidth: Int,
        btnClearVisibility: Int
    ) {
        with(holder) {
            cvCurrency.strokeColor = ContextCompat.getColor(holder.cvCurrency.context, strokeColor)
            cvCurrency.strokeWidth = strokeWidth
            btnClear.visibility = btnClearVisibility
        }
    }

    private fun setBtnClearOnClickListener(btnClear: ImageView, position: Int) {
        btnClear.setOnClickListener {
            savedPosition = position
            onConverterEvent(ConverterEvent.ClearAllValues)
        }
    }

    fun updateCurrencyValues(newCurrencyValues: List<Pair<String, String>>) {
        currencyValues = newCurrencyValues
        notifyDataSetChanged()
    }

    private fun setResourceId(holder: ConverterViewHolder, currency: Pair<String, String>): Int =
        holder.ivCurrency.context.resources.getIdentifier(
            "ic_${currency.first}",
            "drawable",
            holder.ivCurrency.context.packageName
        )

    override fun getItemCount() = currencyValues.size
}
