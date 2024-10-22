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
import com.example.bbank.presentation.base_utils.DecimalDigitsInputFilter
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class ConverterCurrencyAdapter(
    private var currencyValues: List<Pair<String, String>>,
    private val onConverterEvent: (ConverterEvent) -> Unit
) : RecyclerView.Adapter<ConverterCurrencyAdapter.ConverterCurrencyViewHolder>() {

    private var savedPosition = -1

    inner class ConverterCurrencyViewHolder(binding: ItemConverterCurrencyRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrencyImage: ImageView = binding.ivCurrencyImage
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val cvCurrency: MaterialCardView = binding.cvCurrency
        val btnClearCurrency: ImageView = binding.btnClearCurrency
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConverterCurrencyViewHolder {
        val binding =
            ItemConverterCurrencyRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ConverterCurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConverterCurrencyViewHolder, position: Int) {
        val currency = currencyValues[position]
        with(holder) {
            setIsRecyclable(false)
            etCurrencyValue.setText(currency.second)
            tvCurrencyCode.text = currency.first.uppercase(Locale.ROOT)
            etCurrencyValue.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter())
            ivCurrencyImage.setImageResource(setResourceId(holder, currency))
            setupItem(holder, position)
            setCurrencyValueTextChangedListener(etCurrencyValue, position)
            setCurrencyValueOnFocusChangeListener(holder)
            setBtnClearOnClickListener(holder.btnClearCurrency, position)
        }
    }

    private fun setupItem(holder: ConverterCurrencyViewHolder, position: Int) {
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
                onConverterEvent(ConverterEvent.ClearCurrencyValues)
            } else if (it.toString() != ".") {
                val newValue = when {
                    it.toString() == "0" -> "0"
                    it.toString() == "0." -> "0."
                    it.toString().startsWith("0.") -> it.toString()
                    it.toString().startsWith("0") -> it.toString().substring(1)
                    else -> it.toString()
                }
                onConverterEvent(
                    ConverterEvent.ConverterValueChanged(
                        currencyValues[position].first,
                        newValue
                    )
                )
                savedPosition = position
            }
        }
    }

    private fun setCurrencyValueOnFocusChangeListener(holder: ConverterCurrencyViewHolder) {
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
        holder: ConverterCurrencyViewHolder,
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

    private fun setBtnClearOnClickListener(btnClear: ImageView, position: Int) {
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
        holder: ConverterCurrencyViewHolder,
        currency: Pair<String, String>
    ): Int =
        holder.ivCurrencyImage.context.resources.getIdentifier(
            "ic_${currency.first}",
            "drawable",
            holder.ivCurrencyImage.context.packageName
        )

    override fun getItemCount() = currencyValues.size
}
