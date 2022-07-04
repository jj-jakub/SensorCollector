package com.jj.core.domain.server

import com.jj.core.domain.server.requests.RequestType

interface RemoteControlManager {

    suspend fun receiveRequest(requestType: RequestType): Boolean
}