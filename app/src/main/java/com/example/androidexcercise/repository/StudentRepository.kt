package com.example.androidexcercise.repository

import com.example.androidexcercise.Student

class StudentRepository(private val studentDao: StudentDao) {
    fun updateStudentInfo(student: Student) = studentDao.updateStudentInfo(
        id = student.id,
        name = student.name,
        gender = student.gender,
        mathScore = student.mathScore,
        physicScore = student.physicsScore,
        chemistryScore = student.chemistryScore,
        address = student.address
    )

    fun updateStudent(student: Student) = studentDao.updateStudent(student)

    fun getStudentInfo(id: String) = studentDao.getStudent(id)

    suspend fun insertNewStudent(student: Student) = studentDao.insertNewStudent(student)

    suspend fun deleteStudent(student: Student) = studentDao.deleteStudent(student)

    fun getAllStudentInfo(): List<Student> {
        return studentDao.getAllStudent()
    }

    fun getAllStudentInfoLiveData() = studentDao.getAllStudentLiveData()
}