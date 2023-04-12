package com.example.nycschools.home.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nycschools.network.ApiClient
import com.example.nycschools.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SchoolInfoRepository {

    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchSchoolInfo(dbn: String):LiveData<List<SchoolInfoModel>>{
        val data = MutableLiveData<List<SchoolInfoModel>>()

        apiInterface?.fetchSchoolInfo(dbn)?.enqueue(object : Callback<List<SchoolInfoModel>>{

            override fun onFailure(call: Call<List<SchoolInfoModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<SchoolInfoModel>>,
                response: Response<List<SchoolInfoModel>>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }

            }
        })

        return data

    }

}