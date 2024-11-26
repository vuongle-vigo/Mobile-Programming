package com.example.studentman_listview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()
            if (name.isNotEmpty() && id.isNotEmpty()) {
                val student = StudentModel(name, id)

                val resultIntent = Intent()
                resultIntent.putExtra("new_student", student)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
