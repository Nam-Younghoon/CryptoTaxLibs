package com.github.nam_younghoon.cryptotaxlibs.Network


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("/")
    fun init(@FieldMap params: Map<String, String>) : Call<JsonObject>

    @JvmSuppressWildcards
    @POST("/getUserTaxInfo")
    fun getUserTaxInfo(@Body params: JsonObject) : Call<JsonObject>

}