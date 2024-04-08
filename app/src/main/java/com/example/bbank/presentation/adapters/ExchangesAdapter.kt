package com.example.bbank.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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

    private val dayOfWeek: Int
    private val correctedDayOfWeek: Int
    private val currentTime: Int

    init {
        val calendar = Calendar.getInstance()
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        correctedDayOfWeek = if (dayOfWeek == Calendar.SUNDAY) 7 else dayOfWeek
        val (currentTimeH, currentTimeM) = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
            calendar.time
        ).split(":").map { it.toIntOrNull() ?: 0 }
        currentTime = currentTimeH * 60 + currentTimeM
    }

    inner class ExchangesViewHolder(var binding: ExchangeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val chosenExchange = exchanges[position]
                    onClick(chosenExchange)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangesViewHolder {
        val binding = ExchangeRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ExchangesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exchanges.size
    }

    override fun onBindViewHolder(holder: ExchangesViewHolder, position: Int) {
        val chosenExchange = exchanges[position]
        val view = holder.binding

        val fullAddress = "${chosenExchange.nameType} " +
                "${chosenExchange.name}, " +
                "${chosenExchange.streetType}, " +
                "${chosenExchange.street}, " +
                chosenExchange.homeNumber

        val isOpen = isExchangeOpen(chosenExchange.infoWorktime)
        view.viewIsOpen.setBackgroundResource(if (isOpen) R.color.lime_green else R.color.crimson)

        view.tvExchangeAddress.text = fullAddress
        view.tvBuyRate.text = chosenExchange.usdOut
        view.tvSaleRate.text = chosenExchange.usdIn
    }

    private fun isExchangeOpen(infoWorktime: String): Boolean {
        val worktimeParts = infoWorktime.split("|")
        val todayWorktime =
            worktimeParts[correctedDayOfWeek - 1].replaceFirst("[А-Яа-я]+".toRegex(), "").trim()

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

    internal fun updateExchangesAdapterData(newExchanges: List<Exchanges>) {
        exchanges = newExchanges
        notifyDataSetChanged()
    }
}