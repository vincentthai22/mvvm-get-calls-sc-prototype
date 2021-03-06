package com.example.vthai.sidecarprototype.datasource

import com.example.vthai.sidecarprototype.model.Doctor
import com.example.vthai.sidecarprototype.model.DoctorCost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * DoctorDataSource
 *
 * Function: Handles all server calls related to Doctor. Should only be accessed by @DataSourceManager.kt
 */
object DoctorDataSource {

    private const val DOCTOR_OVERVIEW_URL_FORMAT_REQUIRES_ID = "https://c8e4ece5-082f-4c43-aac1-b0215c1f36a4.mock.pstmn.io/doctors/%s"
    private const val DOCTOR_COSTS_URL_FORMAT_REQUIRES_ID = "https://c8e4ece5-082f-4c43-aac1-b0215c1f36a4.mock.pstmn.io/doctors/%s/costs"



    interface Service {
        @GET("/doctors/{id}")
        fun retrieveDoctors(@Path("id") id: String): Call<Doctor>
        @GET("doctors/{id}/costs")
        fun retrieveDoctorCosts(@Path("id") id:String): Call<List<DoctorCost>>
    }




    fun retrieveDoctorCostData(docId: String, listener: DoctorCostsAsyncTask.Listener) {
        DoctorCostsAsyncTask.retrieveDoctorDataFrom(String.format(DOCTOR_COSTS_URL_FORMAT_REQUIRES_ID, docId), listener)
    }

    fun retrieveDoctorOverviewData(@Path("id") docId: String, plainDoctor: Doctor, listener: DoctorOverviewAsyncTask.Listener) {
        DoctorOverviewAsyncTask.retrieveDoctorDataFrom(String.format(DOCTOR_OVERVIEW_URL_FORMAT_REQUIRES_ID, docId), listener, plainDoctor)
    }
}