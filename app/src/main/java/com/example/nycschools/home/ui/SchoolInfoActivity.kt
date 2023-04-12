package com.example.nycschools.home.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nycschools.R
import com.example.nycschools.home.viewmodel.SchoolInfoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_school_info.*

class SchoolInfoActivity : AppCompatActivity() {

    private lateinit var vm: SchoolInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_info)

        ViewCompat.setTransitionName(findViewById(R.id.school_name_TV), VIEW_NAME_SCHOOL_NAME)

        val intent = intent
        val dbn = intent.getStringExtra("dbn")
        val school_name = intent.getStringExtra("school_name")

        school_name_TV.setText("" + school_name)

        vm = ViewModelProvider(this)[SchoolInfoViewModel::class.java]

        vm.fetchSchoolInfo(dbn.toString())

        vm.schoolInfoModelLiveData?.observe(this, Observer {
            if (it!=null){
                if(!it?.isEmpty() == true){
                    reading_avg_score_TV.setText("" + it[0].sat_critical_reading_avg_score)

                    math_avg_score_TV.setText("" + it[0].sat_math_avg_score)
                    writing_avg_score_TV.setText("" + it[0].sat_writing_avg_score)
                }else{
                    showToast("No data for this school")
                }
            }else{
                showToast("Something went wrong")
            }
            progress_school_info.visibility = View.GONE
        })
    }

    private fun showToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
    companion object {

        const val VIEW_NAME_SCHOOL_NAME = "detail:school:name"
    }
}