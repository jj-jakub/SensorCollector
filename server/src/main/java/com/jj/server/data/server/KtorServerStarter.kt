package com.jj.server.data.server

import android.util.Log
import com.jj.domain.server.ServerStarter
import com.jj.domain.server.RequestReceiver
import com.jj.domain.server.model.Endpoint
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
    private val ipProvider: com.jj.domain.server.IPProvider,
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
            get(Endpoint.Home.url) {
                requestReceiver.receive(Endpoint.Home)
                call.respond(mapOf("message" to "Hello home"))
            }
            get(Endpoint.TakePhoto.url) {
                val result = requestReceiver.receive(Endpoint.TakePhoto)
                call.respond(mapOf("message" to "Take photo result: $result"))
            }
            get(Endpoint.Vibrate.url) {
                requestReceiver.receive(Endpoint.Vibrate)
                call.respond(mapOf("message" to "Vibrate"))
            }
        }
    }
}