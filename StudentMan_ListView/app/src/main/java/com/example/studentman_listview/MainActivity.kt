package com.example.studentman_listview

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var studentListView: ListView
    private lateinit var studentAdapter: ArrayAdapter<StudentModel>

    val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        studentListView = findViewById(R.id.list_view_student)
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
        studentListView.adapter = studentAdapter

        // Click on an item to edit
        studentListView.setOnItemClickListener { _, _, position, _ ->
            val selectedStudent = students[position]
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student", selectedStudent)
            intent.putExtra("position", position)
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }

        // Register context menu
        registerForContextMenu(studentListView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Handle options menu (add new student)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_ADD)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedPosition = info.position

        menu?.setHeaderTitle(students[selectedPosition].studentName)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedPosition = info.position

        return when (item.itemId) {
            R.id.menu_edit -> {
                val selectedStudent = students[selectedPosition]
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("student", selectedStudent)
                intent.putExtra("position", selectedPosition)
                startActivityForResult(intent, REQUEST_CODE_EDIT)
                true
            }
            R.id.menu_remove -> {
                students.removeAt(selectedPosition)
                studentAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_ADD -> {
                    val newStudent = data?.getParcelableExtra<StudentModel>("new_student")
                    newStudent?.let {
                        students.add(it)
                        studentAdapter.notifyDataSetChanged()
                    }
                }
                REQUEST_CODE_EDIT -> {
                    val updatedStudent = data?.getParcelableExtra<StudentModel>("updated_student")
                    val position = data?.getIntExtra("position", -1) ?: -1
                    if (updatedStudent != null && position != -1) {
                        students[position] = updatedStudent
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }


    companion object {
        const val REQUEST_CODE_ADD = 1
        const val REQUEST_CODE_EDIT = 2
    }
}
