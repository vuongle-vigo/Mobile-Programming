package vn.edu.hust.studentman

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>, private val activity: Activity): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  private var removedStudent: StudentModel? = null
  private var removedPosition: Int = -1

  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener {
      showEditStudentDialog(holder.itemView.context, student, position)
    }

    holder.imageRemove.setOnClickListener {
      removeStudent(position)
    }
  }

  fun showAddStudentDialog(context: Context) {
    val dialogBuilder = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
    dialogBuilder.setView(dialogView)

    val editTextName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editTextId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    dialogBuilder.setTitle("Add student")
      .setPositiveButton("Save") { _, _ ->
        val newName = editTextName.text.toString()
        val newId = editTextId.text.toString()

        val newStudent = StudentModel(studentName = newName, studentId = newId)
        students.add(newStudent)

        notifyItemChanged(students.size - 1)
      }
      .setNegativeButton("Cancel") {dialog, _ ->
        dialog.dismiss()
      }

    val dialog = dialogBuilder.create()
    dialog.show()

  }

  private fun showEditStudentDialog(context: Context, student: StudentModel, position: Int) {
    val dialogBuilder = AlertDialog.Builder(context)
    val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_student, null)
    dialogBuilder.setView(dialogView)

    val editTextName = dialogView.findViewById<EditText>(R.id.edit_student_name)
    val editTextId = dialogView.findViewById<EditText>(R.id.edit_student_id)

    editTextName.setText(student.studentName)
    editTextId.setText(student.studentId)

    dialogBuilder.setTitle("Edit student info").setPositiveButton("Save") {
      _, _ ->
      val newName = editTextName.text.toString()
      val newId = editTextId.text.toString()

      val updatedStudent = student.copy(studentName = newName, studentId = newId)
      students[position] = updatedStudent

      notifyItemChanged(position)
    }
      .setNegativeButton("Cancel") {
        dialog, _ ->
          dialog.dismiss()
      }
    val dialog = dialogBuilder.create()
    dialog.show()
  }

  private fun removeStudent(position: Int) {
    if (position >= 0 && position < students.size) {
      removedStudent = students[position]
      removedPosition = position

      students.removeAt(position)
      notifyItemRemoved(position)

      val snackbar = Snackbar.make(
        activity.findViewById(android.R.id.content),
        "Sinh viên đã bị xóa",
        Snackbar.LENGTH_LONG
      )
      snackbar.setAction("UNDO") {
        undoRemoveStudent()
      }
      snackbar.show()
    }
  }

  private fun undoRemoveStudent() {
    removedStudent?.let {
      students.add(removedPosition, it)  // Thêm sinh viên trở lại danh sách
      notifyItemInserted(removedPosition)  // Cập nhật lại RecyclerView
    }
  }
}