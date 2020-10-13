package com.example.androidexcercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val mList = ArrayList<Student>()
    private lateinit var mAdapter: StudentAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(activity!!.application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(mainViewModel) {
            listStudent.observe(viewLifecycleOwner) {
                mList.clear()
                mList.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = StudentAdapter(mList, context!!) { student ->
            moveToDetailFragment(student)
        }
        rc_student_list.adapter = mAdapter
        rc_student_list.layoutManager = LinearLayoutManager(activity)

        btn_add_new.setOnClickListener {
            moveToAddNewFragment()
        }

    }

    private fun moveToAddNewFragment() {
        val fragment = AddNewStudentFragment()
        fragmentManager!!.beginTransaction().replace(R.id.container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    private fun moveToDetailFragment(student: Student){
        val fragment = AddNewStudentFragment.getInstance(student, Constants.FLOW_DETAIL)
        fragmentManager!!.beginTransaction().replace(R.id.container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

}