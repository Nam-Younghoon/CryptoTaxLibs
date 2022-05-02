package com.github.nam_younghoon.cryptotaxlibs.Model

import com.google.gson.JsonObject
import com.google.gson.JsonParser

class ResponseBody(
    jsonObject: JsonObject?
) {
    val statusCode: String = jsonObject?.get("statusCode")?.asString ?: ""
    val message: String = jsonObject?.get("message")?.asString ?: "오류"
    val result: JsonObject? = jsonObject?.get("result")?.asJsonObject

}