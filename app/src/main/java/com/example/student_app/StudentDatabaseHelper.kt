package com.example.student_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "student_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "students"
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_AGE = "age"
        private const val COL_CLASS = "class"
        private const val COL_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_AGE INTEGER,
                $COL_CLASS TEXT,
                $COL_ADDRESS TEXT
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, student.name)
            put(COL_AGE, student.age)
            put(COL_CLASS, student.studentClass)
            put(COL_ADDRESS, student.address)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateStudent(student: Student): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, student.name)
            put(COL_AGE, student.age)
            put(COL_CLASS, student.studentClass)
            put(COL_ADDRESS, student.address)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(student: Student): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(student.id.toString()))
    }

    @SuppressLint("Range")
    fun getAllStudents(): ArrayList<Student> {
        val studentList = ArrayList<Student>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                val age = cursor.getInt(cursor.getColumnIndex(COL_AGE))
                val studentClass = cursor.getString(cursor.getColumnIndex(COL_CLASS))
                val address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))

                val student = Student(id, name, age, studentClass, address)
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()

        return studentList
    }
}