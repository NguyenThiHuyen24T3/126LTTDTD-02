package com.example.studentmvvm.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmvvm.databinding.ItemStudentRoomBinding
import com.example.studentmvvm.model.Student

class StudentAdapter(
    private val onDeleteClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(
    StudentDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {

        val binding = ItemStudentRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(
        private val binding: ItemStudentRoomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {

            binding.tvName.text = student.fullName

            binding.tvCode.text =
                "MSSV: ${student.studentCode}"

            binding.tvEmail.text = student.email

            binding.tvGpa.text =
                "GPA: ${student.gpa}"

            binding.tvAvatar.text =
                student.fullName.trim().firstOrNull()
                    ?.uppercaseChar()?.toString() ?: "?"

            binding.btnDelete.setOnClickListener {
                onDeleteClick(student)
            }
        }
    }

    class StudentDiffCallback :
        DiffUtil.ItemCallback<Student>() {

        override fun areItemsTheSame(
            oldItem: Student,
            newItem: Student
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Student,
            newItem: Student
        ): Boolean {
            return oldItem == newItem
        }
    }
}
