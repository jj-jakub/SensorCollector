package com.jj.server.domain.server

interface IPProvider {

    fun getIPAddress(): String
}