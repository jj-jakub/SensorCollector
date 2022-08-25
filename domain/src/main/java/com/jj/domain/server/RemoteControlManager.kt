package com.jj.domain.server

import com.jj.domain.server.model.RequestType

interface RemoteControlManager {
    suspend fun receiveRequest(requestType: RequestType): Boolean
}