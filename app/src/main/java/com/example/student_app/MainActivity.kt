package com.example.student_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentDbHelper: StudentDatabaseHelper

    private lateinit var studentList: ArrayList<Student>
    private lateinit var studentAdapter: StudentAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etClass: EditText
    private lateinit var etAddress: EditText
    private lateinit var btnAddStudent: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        studentDbHelper = StudentDatabaseHelper(this)
        studentList = studentDbHelper.getAllStudents()


        recyclerView = findViewById(R.id.recyclerView)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etClass = findViewById(R.id.etClass)
        etAddress = findViewById(R.id.etAddress)
        btnAddStudent = findViewById(R.id.btnAddStudent)

        recyclerView.layoutManager = LinearLayoutManager(this)
        studentAdapter = StudentAdapter(studentList, { student -> updateStudentDialog(student) }, { student -> deleteStudent(student) })
        recyclerView.adapter = studentAdapter

        btnAddStudent.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toInt()
            val studentClass = etClass.text.toString()
            val address = etAddress.text.toString()

            if (name.isNotEmpty() && studentClass.isNotEmpty() && address.isNotEmpty()) {
                val student = Student(name = name, age = age, studentClass = studentClass, address = address)

                studentDbHelper.addStudent(student)
                studentList.add(student)
                studentAdapter.notifyDataSetChanged()
                clearInputFields()
            } else {
                Toast.makeText(this, "Fill in all details", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateStudentDialog(student: Student) {
        val dialogView = layoutInflater.inflate(R.layout.dialoag_update_student, null)
        val etUpdateName = dialogView.findViewById<EditText>(R.id.etUpdateName)
        val etUpdateAge = dialogView.findViewById<EditText>(R.id.etUpdateAge)
        val etUpdateClass = dialogView.findViewById<EditText>(R.id.etUpdateClass)
        val etUpdateAddress = dialogView.findViewById<EditText>(R.id.etUpdateAddress)

        etUpdateName.setText(student.name)
        etUpdateAge.setText(student.age.toString())
        etUpdateClass.setText(student.studentClass)
        etUpdateAddress.setText(student.address)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Update Student")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                student.name = etUpdateName.text.toString()
                student.age = etUpdateAge.text.toString().toInt()
                student.studentClass = etUpdateClass.text.toString()
                student.address = etUpdateAddress.text.toString()

                studentDbHelper.updateStudent(student)
                studentAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun deleteStudent(student: Student) {
        studentDbHelper.deleteStudent(student)
        studentList.remove(student)
        studentAdapter.notifyDataSetChanged()
    }

    private fun clearInputFields() {
        etName.text.clear()
        etAge.text.clear()
        etClass.text.clear()
        etAddress.text.clear()
    }
}