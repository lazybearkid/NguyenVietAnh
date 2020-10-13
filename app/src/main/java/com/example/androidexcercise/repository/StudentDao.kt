package com.example.androidexcercise.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.androidexcercise.Student

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNewStudent(student: Student): Long

    @Query("UPDATE student SET name = :name, gender = :gender, math_score = :mathScore, physics_score = :physicScore, chemistry_score = :chemistryScore, address = :address WHERE id = :id")
    fun updateStudentInfo(id: Int, name: String, gender: Int, mathScore: Double, physicScore: Double, chemistryScore: Double, address: String): Int

    @Update
    fun updateStudent(student: Student): Int

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudent(id: String): Student

    @Delete
    suspend fun deleteStudent(student: Student): Int

    @Query("SELECT * FROM student")
    fun getAllStudent(): List<Student>

    @Query("SELECT * FROM student")
    fun getAllStudentLiveData(): LiveData<List<Student>>
}