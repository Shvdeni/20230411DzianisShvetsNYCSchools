package com.example.nycschools.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nycschools.home.data.HomeRepository
import com.example.nycschools.home.data.PostModel
import com.example.nycschools.home.data.SchoolInfoModel
import com.example.nycschools.home.data.SchoolInfoRepository

class SchoolInfoViewModel(application: Application): AndroidViewModel(application){

    private var schoolInfoRepository: SchoolInfoRepository?=null
    var schoolInfoModelLiveData:LiveData<List<SchoolInfoModel>>?=null

    init {
        schoolInfoRepository = SchoolInfoRepository()
        schoolInfoModelLiveData = MutableLiveData()
    }

    fun fetchSchoolInfo(dbn: String){
        schoolInfoModelLiveData = schoolInfoRepository?.fetchSchoolInfo(dbn)
    }
}