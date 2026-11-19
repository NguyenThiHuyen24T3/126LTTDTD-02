
package com.example.studentmvvm.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmvvm.R
import com.example.studentmvvm.data.AppDatabase
import com.example.studentmvvm.repository.StudentRepository
import com.example.studentmvvm.viewmodel.StudentViewModel
import com.example.studentmvvm.viewmodel.StudentViewModelFactory

class MainActivity : AppCompatActivity() {

    private val database by lazy {
        AppDatabase.getDatabase(this)
    }

    private val repository by lazy {
        StudentRepository(database.studentDao())
    }

    val viewModelFactory by lazy {
        StudentViewModelFactory(repository)
    }

    private val viewModel: StudentViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Khởi tạo ViewModel và Database
        viewModel

        // Chỉ nạp Fragment khi Activity được tạo lần đầu
        if (savedInstanceState == null) {

            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container_view,
                    StudentListFragment()
                )
                .commit()
        }
    }
}
