package com.jj.sensorcollector.playground1.domain.time

interface TimeProvider {

    fun getNowMillis(): Long
}