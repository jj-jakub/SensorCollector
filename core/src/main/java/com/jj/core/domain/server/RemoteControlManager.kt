package com.jj.core.domain.server

import com.jj.core.domain.server.requests.RequestType

interface RemoteControlManager {

    fun receiveRequest(requestType: RequestType)
}