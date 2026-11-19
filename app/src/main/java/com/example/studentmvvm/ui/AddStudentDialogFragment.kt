package com.example.studentmvvm.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.studentmvvm.databinding.DialogAddStudentBinding
import com.example.studentmvvm.model.Student
import com.example.studentmvvm.viewmodel.StudentViewModel

class AddStudentDialogFragment : DialogFragment() {

    private val viewModel: StudentViewModel by activityViewModels {
        (requireActivity() as MainActivity).viewModelFactory
    }

    override fun onCreateDialog(
        savedInstanceState: Bundle?
    ): Dialog {

        val binding = DialogAddStudentBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        return AlertDialog.Builder(requireContext())
            .setTitle("Thêm sinh viên mới")
            .setView(binding.root)
            .setPositiveButton("Lưu") { _, _ ->

                val name = binding.etName.text
                    .toString().trim()

                val code = binding.etCode.text
                    .toString().trim()

                val email = binding.etEmail.text
                    .toString().trim()

                val gpa = binding.etGpa.text
                    .toString().toDoubleOrNull()

                when {
                    name.isEmpty() ||
                            code.isEmpty() ||
                            email.isEmpty() ||
                            gpa == null -> {

                        Toast.makeText(
                            requireContext(),
                            "Vui lòng nhập đầy đủ thông tin hợp lệ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    gpa !in 0.0..4.0 -> {

                        Toast.makeText(
                            requireContext(),
                            "GPA phải từ 0 đến 4",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {

                        val student = Student(
                            fullName = name,
                            studentCode = code,
                            email = email,
                            gpa = gpa
                        )

                        viewModel.insert(student)

                        Toast.makeText(
                            requireContext(),
                            "Đã thêm sinh viên",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            .setNegativeButton("Hủy", null)
            .create()
    }
}
