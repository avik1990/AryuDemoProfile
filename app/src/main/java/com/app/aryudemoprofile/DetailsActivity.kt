package com.app.aryudemoprofile

import android.content.Context
import android.view.View
import com.app.aryudemoprofile.model.Success
import com.app.aryudemoprofile.others.BaseActivity
import com.app.aryudemoprofile.others.Constants
import com.app.aryudemoprofile.others.LoaderDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailsActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        if (v == iv_back) {
            onBackPressed()
        }
    }

    lateinit var context: Context
    lateinit var _jsonString: String

    private val loader by lazy {
        LoaderDialog(this)
    }

    override fun initResources() {
        context = this
        _jsonString = intent.getStringExtra(Constants.Keys._jsonString)

        empDetailsFetched()
    }

    override fun initListeners() {
        iv_back.setOnClickListener(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_details
    }

    fun empDetailsFetched() {
        val trackresponse = GsonBuilder().create().fromJson(_jsonString, Success::class.java)
        tvHeader.text = trackresponse.name.toUpperCase() + " DETAILS"
        tvEmpcode.text = "Emp Code: " + trackresponse.empcode
        tvName.text = "Emp Name: " + trackresponse.name
        tvCatgory.text = "Emp Cat.: " + trackresponse.category
        tvContact.text = "Emp Ph. : " + trackresponse.contact
        tvAddress.text = "Emp Add.: " + trackresponse.address
        tvDesc.text = "Emp Desc.: " + trackresponse.description

        if (!trackresponse.image.isNullOrBlank()) {

            Glide
                .with(context)
                .load(trackresponse.image)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(profilepic)
        }
    }
}
