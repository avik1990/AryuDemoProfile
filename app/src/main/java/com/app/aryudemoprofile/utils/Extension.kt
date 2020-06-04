package com.app.newsapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.Gravity
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun isConnectedToNetwork(context: Context?): Boolean {
    //return true
    val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}

fun showSnackbar(parentLayoutId: View, msg: String, timeInMillis: Int) {
    Snackbar.make(parentLayoutId, msg, timeInMillis).show()
}

fun showToast(context: Context?,msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}
