package com.example.employeeapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.employeeapp.data.Employee
import com.example.employeeapp.databinding.EmployeeItemBinding

class EmployeeAdapter(
    private val employees: List<Employee>,
    private val onDeleteClickListener: (Employee) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    class EmployeeViewHolder(val binding: EmployeeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding =
            EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        with(holder.binding) {
            employeeName.text = employee.name
            employeeNip.text = employee.nip

            if (employee.photo.isEmpty()) {
                Glide.with(holder.itemView.context)
                    .load("https://ui-avatars.com/api/?name=${employee.name}")
                    .into(employeePhoto)
            } else {
                Glide.with(holder.itemView.context).load(employee.photo)
                    .into(employeePhoto)
            }

            deleteButton.setOnClickListener {
                onDeleteClickListener(employee)
            }
        }
    }

    override fun getItemCount(): Int = employees.size
}