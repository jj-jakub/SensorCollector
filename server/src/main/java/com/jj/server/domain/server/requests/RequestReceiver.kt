package com.jj.server.domain.server.requests

import com.jj.server.domain.server.Endpoint

interface RequestReceiver {

    fun receive(endpoint: Endpoint)
}