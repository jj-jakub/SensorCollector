package com.jj.core.domain.time

interface TimeProvider {

    fun getNowMillis(): Long
}