package com.example.bbank.presentation.departments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemDepartmentRvBinding
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentId
import com.example.core.domain.util.extentions.getAddressAndName
import com.example.core.domain.util.extentions.getTimeInMinutes
import com.example.core.domain.util.extentions.isOpen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class DepartmentsAdapter(
    private var departments: List<Department>,
    private val onClick: (DepartmentId) -> Unit
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentsViewHolder>() {

    private var currentDayOfWeek: Int = 0
    private var currentTime: Int = 0
    private var allDepartments: List<Department> = emptyList()

    init {
        setCurrentTimeProperties()
    }

    internal inner class DepartmentsViewHolder(binding: ItemDepartmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDepartmentAddressAndName: TextView = binding.tvDepartmentAddressAndName
        val tvDepartmentCurrencyBuyRate: TextView = binding.tvDepartmentCurrencyBuyRate
        val tvDepartmentCurrencySaleRate: TextView = binding.tvDepartmentCurrencySaleRate
        val viewDepartmentAccessibility: View = binding.departmentAccessibility
        val clDepartmentItem: ConstraintLayout = binding.clDepartmentItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding =
            ItemDepartmentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        val department = departments[position]
        with(holder) {
            tvDepartmentAddressAndName.text = department.getAddressAndName()
            tvDepartmentCurrencyBuyRate.text = department.usdOut
            tvDepartmentCurrencySaleRate.text = department.usdIn
            viewDepartmentAccessibility.setBackgroundResource(getColorForDepartment(department))
            clDepartmentItem.setOnClickListener { onClick(department.id) }
        }
    }

    override fun getItemCount(): Int = departments.size

    private fun getColorForDepartment(department: Department): Int =
        if (department.isOpen(
                currentTime,
                currentDayOfWeek
            )
        ) R.color.lime_green else R.color.crimson

    private fun setCurrentTimeProperties() {
        val calendar = Calendar.getInstance()
        currentDayOfWeek =
            calendar.get(Calendar.DAY_OF_WEEK).let { if (it == Calendar.SUNDAY) 7 else it - 1 }
        val currentTimeParts =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time).split(":")
        currentTime = getTimeInMinutes(currentTimeParts[0], currentTimeParts[1])
    }

    internal fun updateDepartments(newDepartments: List<Department>) {
        departments = newDepartments
        allDepartments = newDepartments
        notifyDataSetChanged()
    }

    internal fun showOpenDepartments() {
        departments = allDepartments.filter { it.isOpen(currentTime, currentDayOfWeek) }
        notifyDataSetChanged()
    }

    internal fun showAllDepartments() {
        departments = allDepartments
        notifyDataSetChanged()
    }
}