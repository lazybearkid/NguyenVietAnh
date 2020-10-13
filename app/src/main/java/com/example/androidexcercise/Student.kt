package com.example.androidexcercise

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "student")
data class Student(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "gender")
    val gender: Int,

    @ColumnInfo(name = "math_score")
    val mathScore: Double,

    @ColumnInfo(name = "physics_score")
    val physicsScore: Double,

    @ColumnInfo(name = "chemistry_score")
    val chemistryScore: Double,

    @ColumnInfo(name = "address")
    val address: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}