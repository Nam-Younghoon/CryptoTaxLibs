package com.github.nam_younghoon.cryptotaxlibs

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("/")
    fun initServer() : Call<JsonObject>
}