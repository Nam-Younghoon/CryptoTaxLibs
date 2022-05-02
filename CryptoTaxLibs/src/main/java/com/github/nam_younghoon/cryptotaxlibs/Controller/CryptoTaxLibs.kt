package com.github.nam_younghoon.cryptotaxlibs.Controller

import android.accounts.NetworkErrorException
import android.content.Context
import android.util.Log
import com.github.nam_younghoon.cryptotaxlibs.Model.CtLibInfo
import com.github.nam_younghoon.cryptotaxlibs.Model.ResponseBody
import com.github.nam_younghoon.cryptotaxlibs.Network.*
import com.github.nam_younghoon.cryptotaxlibs.Util.AES128Util
import com.google.gson.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

object CryptoTaxLibs {

    private val retrofitServiceImpl = RetrofitClass.getRetrofit().create(RetrofitService::class.java)

    private val ctLibVer = CtLibInfo.CT_LIB_VER.value
    private val ctLibOS = CtLibInfo.CT_LIB_OS.value
    private val ctLibNo = CtLibInfo.CT_LIB_NO.value

    private lateinit var appID: String
    private lateinit var ctServiceNo: String
    private lateinit var userAccessKey: String
    private lateinit var userSecretKey: String

    private fun init(context: Context, callBack: (ResponseBody) -> Unit) {
        if(NetworkStateChecker.checkNetworkState(context)) {
            appID = context.packageName
            ctServiceNo = System.currentTimeMillis().toString()
            retrofitServiceImpl.init(setInit()).enqueue(object: Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful) {
                        val result = ResponseBody(response.body())
                        when(result.statusCode) {
                            "200" -> callBack(result)
                        }
                    }
                }
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    callBack(networkFailResponse(NetworkFailInfo.NETWORK_FAIL))
                }
            })
        } else {
            callBack(networkFailResponse(NetworkFailInfo.IS_NOT_CONNECTED))
        }
    }

    private fun setInit(): Map<String, String> {
        return mapOf(
            "appID" to appID,
            "ctLibVer" to ctLibVer,
            "ctLibOS" to ctLibOS,
            "ctLibNo" to ctLibNo,
            "ctServiceNo" to ctServiceNo
        )
    }

    fun getUserTaxInfo(context: Context, required: Map<String, String>, callBack: (ResponseBody) -> Unit){
        if(!required.containsKey("userCI") || required["userCI"].isNullOrBlank()){
            throw IllegalArgumentException("userCI값은 필수입니다.")
        }
        init(context) {
            when (it.statusCode) {
                "200" -> {
                    retrofitServiceImpl.getUserTaxInfo(setUserTaxInfo(it.result?.get("secretKey")?.asString ?: "", required)).enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            val result = ResponseBody(response.body())
                            when (result.statusCode) {
                                "200" -> callBack(result)

                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            callBack(networkFailResponse(NetworkFailInfo.NETWORK_FAIL))

                        }
                    })
                }
                "E01" -> {
                    callBack(networkFailResponse(NetworkFailInfo.IS_NOT_CONNECTED))
                }
                "E02" -> {
                    callBack(networkFailResponse(NetworkFailInfo.NETWORK_FAIL))
                }
            }

        }
    }

    private fun setUserTaxInfo(secretKey: String, required: Map<String, String>): JsonObject {
        val encryptedCI = AES128Util(secretKey).encrypt(required["userCI"] ?: "")
        userAccessKey = required["userAccessKey"] ?: ""
        userSecretKey = required["userSecretKey"] ?: ""
        val userKeyMap =
            "{\"${encryptedCI}\":{\"${ctLibNo}\":{\"${ctServiceNo}\":{\"userAccessKey\":\"${userAccessKey}\",\"userSecretKey\":\"${userSecretKey}\"}}}}"
        val jsonParser = JsonParser()
        val jsonElement = jsonParser.parse(userKeyMap)

        return jsonElement.asJsonObject
    }

    private fun networkFailResponse(state: NetworkFailInfo) : ResponseBody {
        return when (state) {
            NetworkFailInfo.IS_NOT_CONNECTED -> {
                ResponseBody(NetworkFailRes.isNotConnected())
            }
            NetworkFailInfo.NETWORK_FAIL -> {
                ResponseBody(NetworkFailRes.networkFail())
            }
        }
    }
}