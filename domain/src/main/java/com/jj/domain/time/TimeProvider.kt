package com.jj.domain.time

interface TimeProvider {
    fun getNowMillis(): Long
}