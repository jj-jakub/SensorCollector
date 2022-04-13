package com.jj.sensorcollector.playground1.domain.server.requests

import com.jj.sensorcollector.playground1.domain.server.Endpoint

interface RequestReceiver {

    fun receive(endpoint: Endpoint)
}