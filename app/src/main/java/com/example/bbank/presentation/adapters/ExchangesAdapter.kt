package com.example.bbank.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ExchangeRvItemBinding
import com.example.bbank.domain.models.Exchanges
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class ExchangesAdapter(
    private val context: Context,
    private var exchanges: List<Exchanges>,
    private val onClick: (Exchanges) -> Unit
) : RecyclerView.Adapter<ExchangesAdapter.ExchangesViewHolder>() {

    private var dayOfWeek: Int = 0
    private var currentTime: Int = 0

    init {
        setCurrentTimeProperties()
    }

    private fun setCurrentTimeProperties() {
        val calendar = Calendar.getInstance()

        dayOfWeek = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 7
            else -> calendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        val (currentTimeH, currentTimeM) = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
            calendar.time
        ).split(":").map { it.toIntOrNull() ?: 0 }
        currentTime = currentTimeH * 60 + currentTimeM
    }

    inner class ExchangesViewHolder(binding: ExchangeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvExchangeAddress: TextView = binding.tvExchangeAddress
        val tvBuyRate: TextView = binding.tvBuyRate
        val tvSaleRate: TextView = binding.tvSaleRate
        val departmentAccessibility: View = binding.departmentAccessibility
        val exchangesCardView: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangesViewHolder {
        val binding = ExchangeRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExchangesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exchanges.size
    }

    override fun onBindViewHolder(holder: ExchangesViewHolder, position: Int) {
        val exchange = exchanges[position]

        setExchangesCardViewClick(holder.exchangesCardView, position)

        holder.departmentAccessibility.setBackgroundResource(getColorForDepartment(exchange))
        holder.tvExchangeAddress.text = getFullAddress(exchange)
        holder.tvBuyRate.text = exchange.usdOut
        holder.tvSaleRate.text = exchange.usdIn
    }

    private fun getFullAddress(chosenExchange: Exchanges): CharSequence {
        return "${chosenExchange.nameType} " +
                "${chosenExchange.name}, " +
                "${chosenExchange.streetType}, " +
                "${chosenExchange.street}, " +
                "${chosenExchange.homeNumber}, " +
                chosenExchange.filialsText
    }

    private fun setExchangesCardViewClick(exchangesCardView: CardView, position: Int) {
        exchangesCardView.setOnClickListener {
            onClick(exchanges[position])
        }
    }

    private fun getColorForDepartment(exchange: Exchanges): Int {
        val isOpen = isExchangeOpen(exchange.infoWorktime)
        return if (isOpen) R.color.lime_green else R.color.crimson
    }

    private fun isExchangeOpen(infoWorktime: String): Boolean {
        val worktimeParts = infoWorktime.split("|")
        val todayWorktime =
            worktimeParts[dayOfWeek - 1].replaceFirst("[А-Яа-я]+".toRegex(), "").trim()

        return when {
            todayWorktime.isEmpty() -> false
            else -> {
                val parts = todayWorktime.split(" ").filter { it.isNotBlank() }

                // Open time
                val startTimeH = parts[0].toInt()
                val startTimeM = parts[1].toInt()
                val startTime = startTimeH * 60 + startTimeM

                // Close time
                val endTimeH = parts[2].toInt() // TODO: remove +2 after tests
                val endTimeM = parts[3].toInt()
                val endTime = endTimeH * 60 + endTimeM

                // If in work time
                if (currentTime >= startTime && currentTime < endTime) {
                    //If break is exists
                    if (parts.size > 4) {
                        val breakStartH = parts[4].toInt()
                        val breakStartM = parts[5].toInt()
                        val breakStart = breakStartH * 60 + breakStartM

                        val breakEndH = parts[6].toInt()
                        val breakEndM = parts[7].toInt()
                        val breakEnd = breakEndH * 60 + breakEndM

                        return !(currentTime >= breakStart && currentTime < breakEnd)
                    }
                    return true
                }
                return false
            }
        }
    }

    internal fun updateExchanges(newExchanges: List<Exchanges>) {
        exchanges = newExchanges
        notifyDataSetChanged()
    }
}