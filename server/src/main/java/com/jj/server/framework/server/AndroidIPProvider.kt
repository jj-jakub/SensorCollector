package com.jj.server.framework.server

import android.content.Context
import android.net.ConnectivityManager
import com.jj.core.domain.server.IPProvider

class AndroidIPProvider(
    private val context: Context
) : IPProvider {

    // TODO Add listener for network changes and propagate updated IP
    override fun getIPAddress(): String {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager.activeNetwork
        val linkProperties = connectivityManager.getLinkProperties(activeNetwork)
        return linkProperties?.linkAddresses?.firstOrNull { it.address.toString().startsWith("/192") }?.address.toString()
    }
}