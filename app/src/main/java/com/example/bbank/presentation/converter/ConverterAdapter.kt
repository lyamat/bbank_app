package com.example.bbank.presentation.converter

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
import com.example.bbank.databinding.ItemConverterCurrencyRvBinding
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class ConverterAdapter(
    private var currencyValues: List<Pair<String, String>>,
    private val onConverterEvent: (ConverterEvent) -> Unit
) : RecyclerView.Adapter<ConverterAdapter.ConverterViewHolder>() {

    private var savedPosition = -1

    inner class ConverterViewHolder(binding: ItemConverterCurrencyRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrencyImage: ImageView = binding.ivCurrencyImage
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val cvCurrency: MaterialCardView = binding.cvCurrency
        val btnClearCurrency: ImageView = binding.btnClearCurrency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterViewHolder {
        val binding =
            ItemConverterCurrencyRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ConverterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterViewHolder, position: Int) {
        val currency = currencyValues[position]
        with(holder) {
            setIsRecyclable(false)
            etCurrencyValue.setText(currency.second)
            tvCurrencyCode.text = currency.first.uppercase(Locale.ROOT)
            etCurrencyValue.filters = arrayOf<InputFilter>(CurrencyValueInputFilter())
            ivCurrencyImage.setImageResource(setResourceId(holder, currency))
            setupItem(holder, position)
            setCurrencyValueTextChangedListener(etCurrencyValue, position)
            setCurrencyValueOnFocusChangeListener(holder)
            setBtnClearClickListener(holder.btnClearCurrency, position)
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
        etCurrencyValue.doAfterTextChanged { input ->
            if (input.toString().isBlank()) {
                onConverterEvent(ConverterEvent.ClearCurrencyValues)
            } else {
                onConverterEvent(
                    ConverterEvent.CurrencyValueChanged(
                        currencyValues[position].first,
                        getParsedCurrencyInputValue(input.toString())
                    )
                )
            }
            savedPosition = position
        }
    }

    private fun getParsedCurrencyInputValue(input: String): String {
        if (input.length == 1 && input == ".") {
            return "0."
        }
        return input
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
            btnClearCurrency.visibility = btnClearVisibility
        }
    }

    private fun setBtnClearClickListener(btnClear: ImageView, position: Int) {
        btnClear.setOnClickListener {
            savedPosition = position
            onConverterEvent(ConverterEvent.ClearCurrencyValues)
        }
    }

    fun updateCurrencyValues(newCurrencyValues: List<Pair<String, String>>) {
        currencyValues = newCurrencyValues
        notifyDataSetChanged()
    }

    private fun setResourceId(
        holder: ConverterViewHolder,
        currency: Pair<String, String>
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_${currency.first.lowercase(Locale.getDefault())}",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )

    override fun getItemCount() = currencyValues.size
}
