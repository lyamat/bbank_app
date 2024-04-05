package com.example.bbank.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.CriteriaRvItemBinding
import com.example.bbank.presentation.utils.CriteriaItem

internal class CriteriaAdapter(
    private var items: List<CriteriaItem>,
    private val onCityClick: () -> Unit
) : RecyclerView.Adapter<CriteriaAdapter.CriteriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CriteriaViewHolder {
        val binding =
            CriteriaRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CriteriaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CriteriaViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class CriteriaViewHolder(private val binding: CriteriaRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CriteriaItem) {
            when (item) {
                is CriteriaItem.CityItem -> {
                    binding.tvCriteria.apply {
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location, 0, 0, 0)
                        text = item.city
                    }
                    binding.root.setOnClickListener { onCityClick() }
                }

                is CriteriaItem.OnWorkItem -> {
                    binding.tvCriteria.apply {
                        text = "Working"
                        binding.tvCriteria.setOnClickListener {
                            item.isSelected = !item.isSelected
                            binding.tvCriteria.setBackgroundColor(if (item.isSelected) Color.GREEN else Color.WHITE)
                        }
                    }
                }
            }
        }
    }

    internal fun updateCriteriaAdapterData(newItem: CriteriaItem) {
        items = items.map { item ->
            when (newItem) {
                is CriteriaItem.CityItem ->
                {
                    if (item is CriteriaItem.CityItem) newItem else item
                }
                is CriteriaItem.OnWorkItem ->
                {
                    if (item is CriteriaItem.OnWorkItem) newItem else item
                }
            }
        }
        notifyDataSetChanged()
    }

}


