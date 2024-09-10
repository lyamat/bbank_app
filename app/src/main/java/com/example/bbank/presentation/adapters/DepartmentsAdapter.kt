package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bbank.R
import com.example.bbank.databinding.ItemDepartmentRvBinding
import com.example.bbank.domain.models.Department
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

    inner class DepartmentsViewHolder(binding: ItemDepartmentRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvDepartmentAddress: TextView = binding.tvDepartmentAddress
        val tvBuyRate: TextView = binding.tvBuyRate
        val tvSaleRate: TextView = binding.tvSaleRate
        val departmentAccessibility: View = binding.departmentAccessibility
        val departmentsCardView: CardView = binding.cvDepartmentItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentsViewHolder {
        val binding =
            ItemDepartmentRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DepartmentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentsViewHolder, position: Int) {
        val department = departments[position]
        with(holder) {
            tvDepartmentAddress.text = getFullAddress(department)
            tvBuyRate.text = department.usdOut
            tvSaleRate.text = department.usdIn
            setDepartmentsCardViewClick(departmentsCardView, position)
            departmentAccessibility.setBackgroundResource(getColorForDepartment(department))
        }
    }

    private fun getFullAddress(department: Department): CharSequence =
        "${department.nameType} " +
                "${department.name}, " +
                "${department.streetType} " +
                "${department.street}, " +
                "${department.homeNumber}, " +
                department.filialsText

    private fun setDepartmentsCardViewClick(departmentsCardView: CardView, position: Int) =
        departmentsCardView.setOnClickListener {
            onClick(departments[position])
        }

    // TODO: refactor!
    private fun getColorForDepartment(department: Department): Int {
        if (isDepartmentOpen(department.infoWorktime)) {
            return R.color.lime_green
        }
        return R.color.crimson
    }

    private fun isDepartmentOpen(infoWorkTime: String): Boolean {
        try { // upd: try/catch added cus of shit response from api (), e.g. "...|Пт      00  |..."
            val workTimeParts = infoWorkTime.split("|")
            val todayWorkTime =
                workTimeParts[dayOfWeek - 1].replaceFirst("[А-Яа-я]+".toRegex(), "").trim()

            return when {
                todayWorkTime.isEmpty() -> false
                else -> {
                    val parts = todayWorkTime.split(" ").filter { it.isNotBlank() }
                    // Open time
                    val startTimeH = parts[0].toInt()
                    val startTimeM = parts[1].toInt()
                    val startTime = startTimeH * 60 + startTimeM
                    // Close time
                    val endTimeH = parts[2].toInt()
                    val endTimeM = parts[3].toInt()
                    val endTime = endTimeH * 60 + endTimeM
                    // If in work time
                    if (currentTime in startTime..<endTime) {
                        //If break is exists
                        if (parts.size > 4) {
                            if (parts[4] != "00") { // get shit response from api - e.g. "Пн 10 00 18 00  00  00|..."
                                val breakStartH = parts[4].toInt()
                                val breakStartM = parts[5].toInt()
                                val breakStart = breakStartH * 60 + breakStartM

                                val breakEndH = parts[6].toInt()
                                val breakEndM = parts[7].toInt()
                                val breakEnd = breakEndH * 60 + breakEndM

                                return currentTime !in breakStart..<breakEnd
                            }
                        }
                        return true
                    }
                    return false
                }
            }
        } catch (e: Exception) {
            return false
        }
    }

    private fun setOpenDepartments() {
        openDepartments.addAll(allDepartments.filter { department -> isDepartmentOpen(department.infoWorktime) })
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

    internal fun updateDepartments(newDepartments: List<Department>) {
        openDepartments.clear()
        allDepartments = newDepartments
        setOpenDepartments()
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

    override fun getItemCount(): Int = departments.size
}