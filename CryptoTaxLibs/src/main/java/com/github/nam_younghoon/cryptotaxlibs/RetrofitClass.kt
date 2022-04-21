package com.github.nam_younghoon.cryptotaxlibs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
    fun getRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.1.54:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}