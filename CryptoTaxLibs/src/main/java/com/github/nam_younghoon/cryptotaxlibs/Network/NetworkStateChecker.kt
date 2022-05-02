package com.github.nam_younghoon.cryptotaxlibs.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkStateChecker {
    companion object {
        fun checkNetworkState(context: Context) : Boolean {
            val connectivityManager : ConnectivityManager = context.getSystemService(ConnectivityManager::class.java)
            val network : Network = connectivityManager.activeNetwork ?: return false
            val activeNetwork: NetworkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        }
    }
}