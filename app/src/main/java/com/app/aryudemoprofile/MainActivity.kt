package com.app.aryudemoprofile

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.aryudemoprofile.adapter.TrackingAdapter
import com.app.aryudemoprofile.model.Success
import com.app.aryudemoprofile.model.Tracking
import com.app.aryudemoprofile.others.APIService
import com.app.aryudemoprofile.others.BaseActivity
import com.app.aryudemoprofile.others.Constants
import com.app.aryudemoprofile.others.LoaderDialog
import com.app.newsapplication.utils.isConnectedToNetwork
import com.app.newsapplication.utils.showToast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : BaseActivity(), TrackingAdapter.onRowItemSelected {

    lateinit var context: Context
    lateinit var trackAdapter: TrackingAdapter
    lateinit var jsonbject: String
    lateinit var imageView: ImageView
    var tracking: Tracking? = null

    private val loader by lazy {
        LoaderDialog(this)
    }

    override fun initResources() {
        context=this
        iv_back.visibility= View.GONE
    }

    override fun initListeners() {
        if (isConnectedToNetwork(context)) {
            parsejson()
        } else {
            showToast(context, "No Internet Connection")
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun getPosition(pos: Int, imageView: ImageView) {
        val gson = Gson()
        gson.toJson(tracking!!.Success[pos])
        jsonbject = gson.toJson(tracking!!.Success[pos])
        this.imageView = imageView
        goToNextPage()
    }

    private fun goToNextPage() {
        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "imageMain")
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(Constants.Keys._jsonString, jsonbject)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    private fun parsejson() {
        loader.show()
        val BASE_URL = resources.getString(R.string.base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Constants.setTimeOut())
            .build()

        val redditAPI: APIService
        redditAPI = retrofit.create(APIService::class.java!!)
        val call = redditAPI.getTrackingResponse()
        call.enqueue(object : Callback<Tracking> {

            override fun onResponse(call: Call<Tracking>, response: retrofit2.Response<Tracking>) {
                Log.d("String", "" + response)
                if (response.isSuccessful) {
                    tracking = response.body()
                    if (tracking!!.Success.isNotEmpty()) {
                        inflateAdapter(tracking!!.Success)
                        //showToast(context, "Yeah Done")
                    } else {
                        showToast(context, "Yeah Error")
                    }
                }
                loader.hide()
            }

            override fun onFailure(call: Call<Tracking>, t: Throwable) {
                loader.hide()
            }
        })
    }

    private fun inflateAdapter(listTrack: List<Success>) {
        trackAdapter = TrackingAdapter(context, listTrack, this)
        val mLayoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rc_tracking.layoutManager = mLayoutManager
        rc_tracking.itemAnimator =
            androidx.recyclerview.widget.DefaultItemAnimator()
        rc_tracking.adapter = trackAdapter
    }


}
