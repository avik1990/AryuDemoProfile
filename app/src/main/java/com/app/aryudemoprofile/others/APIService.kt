package com.app.aryudemoprofile.others

import com.app.aryudemoprofile.model.Tracking
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("viewreport.php")
    fun getTrackingResponse(): Call<Tracking>
}