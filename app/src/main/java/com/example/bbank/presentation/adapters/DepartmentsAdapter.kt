package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemDepartmentRvBinding
import com.example.bbank.domain.models.Department
import com.example.bbank.presentation.utils.PresentationUtils.getFullAddress
import com.example.bbank.presentation.utils.TimeUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal class DepartmentsAdapter(
    private var departments: List<Department>,
    private val onClick: (Department) -> Unit
) : RecyclerView.Adapter<DepartmentsAdapter.DepartmentsViewHolder>() {

    private var dayOfWeek: Int = 0
    private var currentTime: Int = 0
    private var allDepartments: List<Department> = mutableListOf()
    private var openDepartments: MutableList<Department> = mutableListOf()

    init {
        setCurrentTimeProperties()
    }

    internal inner class DepartmentsViewHolder(private val binding: ItemDepartmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal fun bind(department: Department) {
            binding.apply {
                tvDepartmentAddress.text = department.getFullAddress()
                tvBuyRate.text = department.usdOut
                tvSaleRate.text = department.usdIn
                departmentAccessibility.setBackgroundResource(getColorForDepartment(department))
                clDepartmentItem.setOnClickListener { onClick(department) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding =
            ItemDepartmentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        holder.bind(departments[position])
    }

    override fun getItemCount(): Int = departments.size

    private fun getColorForDepartment(department: Department): Int {
        return if (isDepartmentOpen(department.infoWorktime)) R.color.lime_green else R.color.crimson
    }

    private fun isDepartmentOpen(infoWorkTime: String): Boolean {
        val workTimeParts = getTodayWorkTime(infoWorkTime) ?: return false
        val openTime = TimeUtils.parseTime(workTimeParts[0], workTimeParts[1])
        val closeTime = TimeUtils.parseTime(workTimeParts[2], workTimeParts[3])

        return TimeUtils.isTimeInRange(
            currentTime,
            openTime,
            closeTime,
            workTimeParts.getOrNull(4)?.toIntOrNull(),
            workTimeParts.getOrNull(6)?.toIntOrNull()
        )
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
        currentTime = currentTimeParts[0].toInt() * 60 + currentTimeParts[1].toInt()
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