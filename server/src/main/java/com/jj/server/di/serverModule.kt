package com.jj.server.di

import com.jj.server.data.server.DefaultRequestDispatcher
import com.jj.server.data.network.RetrofitFactory
import com.jj.domain.server.ServerStarter
import com.jj.domain.server.RequestDispatcher
import com.jj.domain.server.RequestReceiver
import com.jj.server.data.server.AndroidIPProvider
import com.jj.server.data.server.KtorServerStarter
import com.jj.server.data.server.KtorRequestReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serverModule = module {

    single<com.jj.domain.server.IPProvider> { AndroidIPProvider(androidContext()) }
    single<RequestDispatcher> { DefaultRequestDispatcher(get()) }
    single<RequestReceiver> { KtorRequestReceiver(get()) }
    single<ServerStarter> { KtorServerStarter(get(), get()) }

    single { RetrofitFactory() }
}