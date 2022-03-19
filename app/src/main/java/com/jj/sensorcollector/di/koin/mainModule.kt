package com.jj.sensorcollector.di.koin

import com.jj.sensorcollector.data.network.RetrofitFactory
import com.jj.sensorcollector.data.text.VersionTextProvider
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitFactory() }
    single { VersionTextProvider() }
}