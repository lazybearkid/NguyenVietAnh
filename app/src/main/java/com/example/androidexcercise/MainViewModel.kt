package com.example.androidexcercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidexcercise.repository.StudentRepository
import com.example.androidexcercise.repository.StudentRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StudentRepository

    val addNewSuccess =  MutableLiveData<Any>()
    val addNewFail =  MutableLiveData<Any>()

    val deleteSuccess = MutableLiveData<Any>()
    val deleteFail = MutableLiveData<Any>()

    val updateSuccess = MutableLiveData<Any>()
    val updateFail = MutableLiveData<Any>()

    val listStudent: LiveData<List<Student>>

    val selectedStudent = MutableLiveData<Student>()


    init {
        val studentDao = StudentRoomDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        listStudent = repository.getAllStudentInfoLiveData()
    }


    fun getAllStudentInfo() = repository.getAllStudentInfo()

    fun getStudentInfo(id: String) = repository.getStudentInfo(id)

    fun addNewStudent(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.insertNewStudent(student)
        if (result != -1L){
            addNewSuccess.postValue(null)
        } else {
            addNewFail.postValue(null)
        }
    }

    fun updateStudentInfo(student: Student) = viewModelScope.launch (Dispatchers.IO){
        val result = repository.updateStudent(student)
        if (result != -1){
            updateSuccess.postValue(null)
        } else {
            updateFail.postValue(null)
        }
    }

    fun deleteStudent(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.deleteStudent(student)
        if (result != -1){
            deleteSuccess.postValue(null)
        } else {
            deleteFail.postValue(null)
        }
    }
}