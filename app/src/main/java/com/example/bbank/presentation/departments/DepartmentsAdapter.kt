package com.example.bbank.presentation.departments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemDepartmentRvBinding
import com.example.bbank.presentation.base_utils.TimeUtils
import com.example.core.domain.department.Department
import com.example.core.domain.department.DepartmentId
import com.example.core.domain.util.extentions.getFullAddress
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class DepartmentsAdapter(
    private var departments: List<Department>,
    private val onClick: (DepartmentId) -> Unit
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentsViewHolder>() {

    private var dayOfWeek: Int = 0
    private var currentTime: Int = 0
    private var allDepartments: List<Department> = mutableListOf()
    private var openDepartments: MutableList<Department> = mutableListOf()

    init {
        setCurrentTimeProperties()
    }

    internal inner class DepartmentsViewHolder(binding: ItemDepartmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDepartmentAddress: TextView = binding.tvDepartmentAddress
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
            tvDepartmentAddress.text = department.getFullAddress()
            tvDepartmentCurrencyBuyRate.text = department.usdOut
            tvDepartmentCurrencySaleRate.text = department.usdIn
            viewDepartmentAccessibility.setBackgroundResource(getColorForDepartment(department))
            clDepartmentItem.setOnClickListener { onClick(department.id) }
        }
    }

    override fun getItemCount(): Int = departments.size

    private fun getColorForDepartment(department: Department): Int {
        return if (isDepartmentOpen(department.infoWorktime)) R.color.lime_green else R.color.crimson
    }

    private fun isDepartmentOpen(infoWorkTime: String): Boolean {
        return try {
            val workTimeParts = getTodayWorkTime(infoWorkTime) ?: return false
            val openTime = TimeUtils.getTimeInMinutes(workTimeParts[0], workTimeParts[1])
            val closeTime = TimeUtils.getTimeInMinutes(workTimeParts[2], workTimeParts[3])

            TimeUtils.isTimeInRange(
                currentTime,
                openTime,
                closeTime,
                workTimeParts.getOrNull(4)?.toIntOrNull(),
                workTimeParts.getOrNull(6)?.toIntOrNull()
            )
        } catch (e: Exception) {
            return false
        }
    }

    private fun getTodayWorkTime(infoWorkTime: String): List<String>? {
        return try {
            val workTimeParts = infoWorkTime.split("|")
            val todayWorkTime =
                workTimeParts[dayOfWeek - 1].replaceFirst("[А-Яа-я]+".toRegex(), "").trim()
            todayWorkTime.takeIf { it.isNotEmpty() }?.split(" ")?.filter { it.isNotBlank() }
        } catch (e: Exception) {
            null
        }
    }

    private fun setCurrentTimeProperties() {
        val calendar = Calendar.getInstance()
        dayOfWeek =
            calendar.get(Calendar.DAY_OF_WEEK).let { if (it == Calendar.SUNDAY) 7 else it - 1 }
        val currentTimeParts =
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time).split(":")
        currentTime = TimeUtils.getTimeInMinutes(currentTimeParts[0], currentTimeParts[1])
    }

    internal fun updateDepartments(newDepartments: List<Department>) {
        allDepartments = newDepartments
        openDepartments.clear()
        openDepartments.addAll(newDepartments.filter { isDepartmentOpen(it.infoWorktime) })
        departments = newDepartments
        notifyDataSetChanged()
    }

    internal fun showOnlyOpenDepartments() {
        departments = openDepartments
        notifyDataSetChanged()
    }

    internal fun showAllDepartments() {
        departments = allDepartments
        notifyDataSetChanged()
    }
}