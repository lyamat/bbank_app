package com.example.bbank.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.DepartmentRvItemBinding
import com.example.bbank.domain.models.Department
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class DepartmentsAdapter(
    private val context: Context,
    private var departments: List<Department>,
    private val onClick: (Department) -> Unit
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentsViewHolder>() {

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

    inner class DepartmentsViewHolder(binding: DepartmentRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDepartmentAddress: TextView = binding.tvDepartmentAddress
        val tvBuyRate: TextView = binding.tvBuyRate
        val tvSaleRate: TextView = binding.tvSaleRate
        val departmentAccessibility: View = binding.departmentAccessibility
        val departmentsCardView: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding = DepartmentRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return DepartmentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return departments.size
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        val department = departments[position]

        setDepartmentsCardViewClick(holder.departmentsCardView, position)

        holder.departmentAccessibility.setBackgroundResource(getColorForDepartment(department))
        holder.tvDepartmentAddress.text = getFullAddress(department)
        holder.tvBuyRate.text = department.usdOut
        holder.tvSaleRate.text = department.usdIn
    }

    private fun getFullAddress(department: Department): CharSequence {
        return "${department.nameType} " +
                "${department.name}, " +
                "${department.streetType}, " +
                "${department.street}, " +
                "${department.homeNumber}, " +
                department.filialsText
    }

    private fun setDepartmentsCardViewClick(departmentsCardView: CardView, position: Int) {
        departmentsCardView.setOnClickListener {
            onClick(departments[position])
        }
    }

    private fun getColorForDepartment(department: Department): Int {
        val isOpen = isDepartmentOpen(department.infoWorktime)
        return if (isOpen) R.color.lime_green else R.color.crimson
    }

    private fun isDepartmentOpen(infoWorktime: String): Boolean {
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
                        if (parts[4] != "00") { // get shit response from api (Пн 10 00 18 00  00  00)
                            val breakStartH = parts[4].toInt()
                            val breakStartM = parts[5].toInt()
                            val breakStart = breakStartH * 60 + breakStartM

                            val breakEndH = parts[6].toInt()
                            val breakEndM = parts[7].toInt()
                            val breakEnd = breakEndH * 60 + breakEndM

                            return !(currentTime >= breakStart && currentTime < breakEnd)
                        }


                    }
                    return true
                }
                return false
            }
        }
    }

    internal fun updateDepartments(newDepartments: List<Department>) {
        departments = newDepartments
        notifyDataSetChanged()
    }
}