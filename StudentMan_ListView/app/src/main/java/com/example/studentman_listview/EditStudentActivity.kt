package com.example.studentman_listview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EditStudentActivity : AppCompatActivity() {
    private lateinit var editName: EditText
    private lateinit var editId: EditText
    private lateinit var btnSave: Button
    private var position: Int = -1  // Vị trí của sinh viên trong danh sách

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val student = intent.getParcelableExtra<StudentModel>("student")
        position = intent.getIntExtra("position", -1)

        editName = findViewById(R.id.editTextStudentName)
        editId = findViewById(R.id.editTextStudentId)
        btnSave = findViewById(R.id.buttonSave)

        student?.let {
            editName.setText(it.studentName)
            editId.setText(it.studentId)
        }

        btnSave.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedId = editId.text.toString()

            if (updatedName.isNotEmpty() && updatedId.isNotEmpty()) {
                val updatedStudent = StudentModel(updatedName, updatedId)

                val resultIntent = Intent()
                resultIntent.putExtra("updated_student", updatedStudent)
                resultIntent.putExtra("position", position)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
