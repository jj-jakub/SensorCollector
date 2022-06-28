package com.jj.server.framework.server

import android.util.Log
import com.jj.core.domain.server.IPProvider
import com.jj.server.domain.server.ServerStarter
import com.jj.server.domain.server.requests.RequestReceiver
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.Exception

class KtorServerStarter(
    private val ipProvider: IPProvider,
    private val requestReceiver: RequestReceiver
) : ServerStarter {

    override fun startServer(port: Int) {
        try {
            embeddedServer(Netty, port) {
                install(ContentNegotiation) {
                    gson {}
                }
                registerRoutes()
            }.start(wait = false)

            Log.d("ABABS", "My IP is: ${ipProvider.getIPAddress()}")
        } catch (e: Exception) {
            Log.e("ABAB", "Failed to start server")
            // TODO Return status
        }
    }

    private fun Application.registerRoutes() {
        routing {
            get(com.jj.server.domain.server.Endpoint.Home.url) {
                requestReceiver.receive(com.jj.server.domain.server.Endpoint.Home)
                call.respond(mapOf("message" to "Hello home"))
            }
            get(com.jj.server.domain.server.Endpoint.TakePhoto.url) {
                requestReceiver.receive(com.jj.server.domain.server.Endpoint.TakePhoto)
                call.respond(mapOf("message" to "Take photo"))
            }
            get(com.jj.server.domain.server.Endpoint.Vibrate.url) {
                requestReceiver.receive(com.jj.server.domain.server.Endpoint.Vibrate)
                call.respond(mapOf("message" to "Vibrate"))
            }
        }
    }
}