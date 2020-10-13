package com.example.androidexcercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_add_new_student.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddNewStudentFragment : Fragment() {
    private var flow: Int = 0
    private var student: Student? = null

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flow = it.getInt(Constants.KEY_FLOW)
            student = it.getParcelable(Constants.KEY_STUDENT)
        }

        mainViewModel = MainViewModel(activity!!.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_student, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(mainViewModel) {
            addNewSuccess.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "Add new success", Toast.LENGTH_SHORT).show()
                iv_back.performClick()
            }

            addNewFail.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "Add new failed", Toast.LENGTH_SHORT).show()
            }

            deleteSuccess.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "Delete success", Toast.LENGTH_SHORT).show()
                iv_back.performClick()
            }

            deleteFail.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "Delete failed", Toast.LENGTH_SHORT).show()
            }

            updateSuccess.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "Update success", Toast.LENGTH_SHORT).show()
                iv_back.performClick()
            }
            updateFail.observe(viewLifecycleOwner) {
                Toast.makeText(activity, "update fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        radio_female.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                edt_gender.setText("Female")
            }
        }

        radio_male.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                edt_gender.setText("Male")
            }
        }

        iv_back.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        iv_delete.setOnClickListener {
            student?.let {
                mainViewModel.deleteStudent(it)
            }
        }

        iv_edit.setOnClickListener {
            edt_name.isEnabled = true
            edt_math.isEnabled = true
            edt_physics.isEnabled = true
            edt_chemistry.isEnabled = true
            edt_address.isEnabled = true

            btn_save.visibility = View.VISIBLE
            radio_group.visibility = View.VISIBLE
        }

        btn_save.setOnClickListener {
            if (flow == Constants.FLOW_ADD_NEW) {
                addNewStudent()
            } else {
                updateStudent()
            }
        }
    }

    private fun updateStudent() {
        if (validate()) {
            var gender = 0
            gender = if (edt_gender.text.toString() == "Female") {
                Constants.FEMALE
            } else {
                Constants.MALE
            }
            val student = Student(
                name = edt_name.text.toString(),
                gender = gender,
                mathScore = edt_math.text.toString().toDouble(),
                physicsScore = edt_physics.text.toString().toDouble(),
                chemistryScore = edt_chemistry.text.toString().toDouble(),
                address = edt_address.text.toString()
            )
            this.student?.let {
                student.id = it.id
            }
            mainViewModel.updateStudentInfo(student)
        }
    }

    private fun setupUI() {
        if (flow == Constants.FLOW_DETAIL) {
            tv_screen_name.text = getString(R.string.student_info)
            student?.let {
                edt_name.setText(it.name)
                edt_name.isEnabled = false

                if (it.gender == Constants.FEMALE) {
                    edt_gender.setText("Female")
                    radio_female.isChecked = true
                } else {
                    edt_gender.setText("Male")
                    radio_male.isChecked = true
                }

                edt_math.setText(it.mathScore.toString())
                edt_math.isEnabled = false

                edt_physics.setText(it.physicsScore.toString())
                edt_physics.isEnabled = false

                edt_chemistry.setText(it.chemistryScore.toString())
                edt_chemistry.isEnabled = false

                edt_address.setText(it.address)
                edt_address.isEnabled = false
            }
            iv_edit.visibility = View.VISIBLE
            iv_delete.visibility = View.VISIBLE
            btn_save.visibility = View.GONE
            radio_group.visibility = View.GONE
        } else {
            radio_group.visibility = View.VISIBLE
            tv_screen_name.text = getString(R.string.student_add_new)
            iv_edit.visibility = View.GONE
            iv_delete.visibility = View.GONE
        }
    }

    private fun addNewStudent() {
        if (validate()) {
            val student = Student(
                name = edt_name.text.toString(),
                gender = edt_gender.text.toString().toInt(),
                mathScore = edt_math.text.toString().toDouble(),
                physicsScore = edt_physics.text.toString().toDouble(),
                chemistryScore = edt_chemistry.text.toString().toDouble(),
                address = edt_address.text.toString()
            )
            mainViewModel.addNewStudent(student)
        }
    }

    private fun validate(): Boolean {
        var error: String = ""
        if (edt_address.text.isNullOrEmpty()) {
            error = "address must not be empty"
        }

        if (edt_chemistry.text.isNullOrEmpty()) {
            error = "Chemistry score must not be empty"
        }

        if (edt_physics.text.isNullOrEmpty()) {
            error = "Physics score must not be empty"
        }

        if (edt_math.text.isNullOrEmpty()) {
            error = "Math score must not be empty"
        }

        if (edt_gender.text.isNullOrEmpty()) {
            error = "Gender must not be empty"
        }
        if (edt_name.text.isNullOrEmpty()) {
            error = "Name must not be empty"
        }

        return if (error.isNotEmpty()) {
            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    companion object {
        fun getInstance(student: Student, flow: Int): AddNewStudentFragment {
            val fragment = AddNewStudentFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.KEY_STUDENT, student)
            bundle.putInt(Constants.KEY_FLOW, flow)
            fragment.arguments = bundle
            return fragment
        }
    }
}