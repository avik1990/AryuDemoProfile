package com.app.aryudemoprofile

interface DataSourceCallBack<T> {
    fun onSuccess(responseData: T)
    fun onError(errorMsg : String)
}