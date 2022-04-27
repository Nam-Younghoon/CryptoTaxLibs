package com.github.nam_younghoon.cryptotaxlibs

import android.content.Context
import android.content.pm.PackageManager
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CryptoTaxLibs {

    private val requestData = RetrofitClass.getRetrofit().create(RetrofitService::class.java)

    private const val cryptotaxLibVersion = "1.0.0"
    private const val cryptotaxLibPlatform = "AOS"
    private const val cryptotaxTradeNumber = 4885

    private lateinit var appID : String
    private lateinit var secretKey : String
    private lateinit var cryptotaxServiceNumber : String


    private fun init(context: Context) {
        appID = context.packageName
        val bodyMap = mapOf<String, Any>(
            "appID" to appID,
            "cryptotaxLibVersion" to cryptotaxLibVersion,
            "cryptotaxLibPlatform" to cryptotaxLibPlatform,
            "cryptotaxTradeNumber" to cryptotaxTradeNumber
        )

        val result = requestData.init(bodyMap).execute().body()

        secretKey = result?.get("result")?.asJsonObject?.get("secretKey")?.asString ?: ""
        cryptotaxServiceNumber = result?.get("result")?.asJsonObject?.get("cryptotaxServiceNumber")?.asString ?: ""

    }

    fun getUserTaxInfo(context: Context, userCI: String, resultCallback: (String) -> Unit) {
        init(context)
        val encryptedCI = AES128Util(secretKey).encrypt(userCI)
        val bodyMap = mapOf<String, Any>(
            "appID" to appID,
            "cryptotaxServiceNumber" to cryptotaxServiceNumber,
            "userCI" to encryptedCI
        )
        requestData.getUserTaxInfo(bodyMap).enqueue(object: Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val result = ResponseBody(response.body())
                when(result.statusCode) {
                    in 200..300 -> {
                        val taxInfo = result.result?.get("taxInfo")?.asString ?: ""
                        resultCallback(AES128Util(secretKey).decrypt(taxInfo))
                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })

    }



}

class ResponseBody(
    jsonObject: JsonObject?
) {
    val statusCode : Int = jsonObject?.get("statusCode")?.asInt ?: 0
    val serverMessage : String = jsonObject?.get("serverMessage")?.asString ?: "오류"
    val result : JsonObject? = jsonObject?.get("result")?.asJsonObject

}