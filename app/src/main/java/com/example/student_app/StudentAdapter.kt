package com.example.student_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter (
    private val students: ArrayList<Student>,
    private val updateCallback: (Student) -> Unit,
    private val deleteCallback: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
}
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        private val tvClass: TextView = itemView.findViewById(R.id.tvClass)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val btnUpdate: Button = itemView.findViewById(R.id.btnUpdate)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(student: Student) {
            tvName.text = student.name
            tvAge.text = "Age: ${student.age}"
            tvClass.text = "Class: ${student.studentClass}"
            tvAddress.text = "Address: ${student.address}"

            btnUpdate.setOnClickListener {
                updateCallback(student)
            }

            btnDelete.setOnClickListener {
                deleteCallback(student)
            }
        }
    }
}