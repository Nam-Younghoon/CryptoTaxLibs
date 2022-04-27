package com.github.nam_younghoon.cryptotaxlibs

object DoubleRequestChecker {

    var requestList: MutableList<String> = ArrayList<String>()

    fun addRequest(method: String) : Boolean {
        val key = generateKey(method)
        if(requestList.contains(key)) {
            return false
        }
        requestList.add(key)
        return true
    }

    private fun generateKey(method: String) : String {
        return method
    }

    fun clearAll() {
        requestList.clear()
    }

    fun clear(method: String) {
        val key = generateKey(method)
        requestList.remove(key)
    }
}