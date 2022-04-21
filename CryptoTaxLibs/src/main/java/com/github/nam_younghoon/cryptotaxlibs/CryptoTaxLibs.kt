package com.github.nam_younghoon.cryptotaxlibs

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class CryptoTaxLibs {

    private val requestData = RetrofitClass.getRetrofit().create(RetrofitService::class.java)


    fun initServerData(resultCallback: ResultCallback<JsonObject>) {
        requestData.initServer().enqueue(resultCallback)
    }

}