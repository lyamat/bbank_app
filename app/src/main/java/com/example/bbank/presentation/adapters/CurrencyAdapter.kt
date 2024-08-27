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
import com.example.bbank.domain.models.ConversionRate
import com.example.bbank.presentation.utils.DecimalDigitsInputFilter
import com.example.bbank.presentation.utils.UiText
import com.google.android.material.card.MaterialCardView
import java.util.Locale

internal class CurrencyAdapter(
    private var currencyValues: List<Pair<String, String>>,
    private val conversionRates: List<ConversionRate>,
    private val messageCallback: (UiText) -> Unit

) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private var buySaleStatus: String = "in"
    private var savedPosition = -1

    internal inner class CurrencyViewHolder(
        binding: ItemCurrencyRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val etCurrencyValue: EditText = binding.etCurrencyValue
        val ivCurrency: ImageView = binding.ivCurrency
        val tvCurrencyCode: TextView = binding.tvCurrencyCode
        val cvCurrency: MaterialCardView = binding.cvCurrency
        val btnClear: ImageView = binding.btnClear
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CurrencyViewHolder {
        val binding =
            ItemCurrencyRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
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

    private fun setupItem(holder: CurrencyViewHolder, position: Int) {
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
                updateCurrencyValues(currencyValues.map { Pair(it.first, "0") })
            } else if (it.toString() != ".") {
                val newValue = when {
                    it.toString() == "0." -> "0."
                    it.toString().startsWith("0.") -> it.toString()
                    it.toString().startsWith("0") -> it.toString().substring(1)
                    else -> it.toString()
                }

                val newCurrencyValues =
                    getNewCurrencyValues(currencyValues, currencyValues[position].first, newValue)
                savedPosition = position
                updateCurrencyValues(newCurrencyValues)
            }

        }
    }

    private fun setCurrencyValueOnFocusChangeListener(holder: CurrencyViewHolder) {
        with(holder) {
            etCurrencyValue.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (etCurrencyValue.text.toString() == "Infinity" || etCurrencyValue.text.toString() == "NaN") {
                        etCurrencyValue.isCursorVisible = false
                        etCurrencyValue.clearFocus()
                        messageCallback(UiText.StringResource(R.string.currency_not_available))
                    } else {
                        setCurrencyViewAppearance(this, R.color.lime_green, 6, View.VISIBLE)
                    }
                } else {
                    setCurrencyViewAppearance(this, R.color.gray, 1, View.GONE)
                }
            }
        }
    }

    private fun setCurrencyViewAppearance(
        holder: CurrencyViewHolder,
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
            updateCurrencyValues(currencyValues.map { Pair(it.first, "0") })
        }
    }

    private fun setResourceId(holder: CurrencyViewHolder, currency: Pair<String, String>): Int =
        holder.ivCurrency.context.resources.getIdentifier(
            "ic_${currency.first}", "drawable", holder.ivCurrency.context.packageName
        )

    private fun getNewCurrencyValues(
        currencyValues: List<Pair<String, String>>, currencyCode: String, newValue: String
    ): List<Pair<String, String>> {
        return currencyValues.map { pair ->
            if (pair.first == currencyCode) {
                pair.copy(second = newValue)
            } else {
                val conversionRate = getConversionRate(currencyCode, pair.first)
                var newPairValue = String.format(
                    Locale.US, "%.2f", newValue.replace(',', '.').toDouble() * conversionRate
                )
                if (newPairValue == "0.00") {
                    newPairValue = "0"
                }
                pair.copy(second = newPairValue)
            }
        }
    }

    private fun getConversionRate(from: String, to: String): Double {
        val conversionRate =
            conversionRates.find { it.from == from + "_$buySaleStatus" && it.to == to + "_$buySaleStatus" }
        return conversionRate?.rate ?: 1.0
    }

    internal fun updateCurrencyValues(newCurrencyValues: List<Pair<String, String>>) {
        currencyValues = newCurrencyValues.toList()
        notifyDataSetChanged()
    }

    internal fun showRates(status: String) {
        buySaleStatus = status
        val position = if (savedPosition == -1) 0 else savedPosition
        val currency = currencyValues[position]
        updateCurrencyValues(getNewCurrencyValues(currencyValues, currency.first, currency.second))
    }

    internal fun getCurrencyValues(): List<Pair<String, String>> = currencyValues

    override fun getItemCount() = currencyValues.size
}