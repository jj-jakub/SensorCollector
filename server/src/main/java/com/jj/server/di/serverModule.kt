package com.jj.server.di

import com.jj.server.data.server.DefaultRequestDispatcher
import com.jj.server.framework.server.AndroidIPProvider
import com.jj.server.framework.server.KtorServerStarter
import com.jj.server.framework.server.requests.KtorRequestReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serverModule = module {

    single<com.jj.server.domain.server.IPProvider> { AndroidIPProvider(androidContext()) }
    single<com.jj.server.domain.server.requests.RequestDispatcher> { DefaultRequestDispatcher(get()) }
    single<com.jj.server.domain.server.requests.RequestReceiver> { KtorRequestReceiver(get()) }
    single<com.jj.server.domain.server.ServerStarter> { KtorServerStarter(get(), get()) }
}