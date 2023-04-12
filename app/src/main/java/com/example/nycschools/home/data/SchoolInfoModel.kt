package com.example.nycschools.home.data

data class SchoolInfoModel(

    var school_name:String?="",
    var num_of_sat_test_takers:String?="",
    var sat_critical_reading_avg_score:String?=""
) {
    val sat_math_avg_score: String?=""
    val sat_writing_avg_score: String?=""
}