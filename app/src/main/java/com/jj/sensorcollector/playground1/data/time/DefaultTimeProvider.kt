package com.jj.sensorcollector.playground1.data.time

import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import java.util.Date

class DefaultTimeProvider : TimeProvider {

    override fun getNowMillis(): Long = Date().time
}