package com.ute.profilesinhvien

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.profilesinhvien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentStudent = Student(
        id = "2415053122323",
        name = "Nguyen Thi Huyen",
        className = "24T3",
        major = "Công nghệ thông tin",
        gpa = 3.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hiển thị thông tin sinh viên ban đầu
        bindStudentData(currentStudent)

        // Xử lý khi nhấn nút cập nhật GPA
        binding.btnUpdateGpa.setOnClickListener {

            val inputStr = binding.edtNewGpa.text
                .toString()
                .trim()

            val newGpa = inputStr.toDoubleOrNull()

            // Kiểm tra GPA
            if (newGpa == null || newGpa !in 0.0..4.0) {

                binding.edtNewGpa.error =
                    "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"

                toast("Điểm GPA không hợp lệ")

                return@setOnClickListener
            }

            // Tạo Student mới bằng hàm copy()
            currentStudent = currentStudent.copy(
                gpa = newGpa
            )

            // Hiển thị lại thông tin
            bindStudentData(currentStudent)

            // Xóa nội dung ô nhập
            binding.edtNewGpa.text.clear()

            toast("Cập nhật điểm thành công!")
        }
    }

    private fun bindStudentData(student: Student) {

        with(binding) {

            tvName.text = student.name

            tvStudentId.text =
                "MSSV: ${student.id}"

            tvClassName.text =
                "Lớp: ${student.className}"

            tvMajor.text =
                "Ngành: ${student.major}"

            tvGpaBadge.text =
                "GPA: ${student.gpa}\n${student.gpa.toAcademicRanking()}"
        }
    }
}