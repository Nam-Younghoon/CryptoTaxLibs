package com.github.nam_younghoon.cryptotaxlibs.Network

import com.google.gson.JsonObject
import com.google.gson.JsonParser

object NetworkFailRes {

    //E01
    fun isNotConnected() : JsonObject {
        val errorString = "{\"statusCode\":\"E01\",\"message\":\"${NetworkFailInfo.IS_NOT_CONNECTED.value}\",\"result\":{}}"
        val jsonParser = JsonParser()
        val jsonElement = jsonParser.parse(errorString)
        return jsonElement.asJsonObject
    }

    //E02
    fun networkFail() : JsonObject {
        val errorString = "{\"statusCode\":\"E02\",\"message\":\"${NetworkFailInfo.NETWORK_FAIL.value}\",\"result\":{}}"
        val jsonParser = JsonParser()
        val jsonElement = jsonParser.parse(errorString)
        return jsonElement.asJsonObject
    }
}

enum class NetworkFailInfo(
    val value: String
) {
    IS_NOT_CONNECTED("셀룰러/와이파이를 연결해주세요."),
    NETWORK_FAIL("네트워크 통신상태가 원활하지 않습니다.")
}