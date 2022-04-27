package com.github.nam_younghoon.cryptotaxlibs


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("/")
    fun init(@FieldMap params: Map<String, Any>) : Call<JsonObject>

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("/getUserTaxInfo")
    fun getUserTaxInfo(@FieldMap params: Map<String, Any>) : Call<JsonObject>

}