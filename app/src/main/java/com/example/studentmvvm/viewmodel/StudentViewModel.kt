
package com.example.studentmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.studentmvvm.model.Student
import com.example.studentmvvm.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : ViewModel() {

    val allStudents: LiveData<List<Student>> =
        repository.allStudents.asLiveData()

    fun insert(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(student)
        }
    }

    fun update(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(student)
        }
    }

    fun delete(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(student)
        }
    }
}
