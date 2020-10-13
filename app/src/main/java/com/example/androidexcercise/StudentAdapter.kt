package com.example.androidexcercise

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_item_student.view.*

class StudentAdapter(private val mListStudent: List<Student>,
                     private val context: Context,
                     private val callback: ( (Student) -> Unit)?) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListStudent.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = mListStudent[position]
        holder.itemView.tv_student_name.text = student.name
        holder.itemView.tv_student_scores.text = String.format(
            context.getString(R.string.scores),
            student.mathScore,
            student.physicsScore,
            student.chemistryScore
        )
        if (student.gender == Constants.FEMALE) {
            holder.itemView.student_gender.text = "Female"
        } else {
            holder.itemView.student_gender.text = "Male"
        }

        holder.itemView.setOnClickListener {
            callback?.invoke(student)
        }
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}