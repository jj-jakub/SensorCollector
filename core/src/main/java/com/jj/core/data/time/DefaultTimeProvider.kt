package com.jj.core.data.time

import com.jj.core.domain.time.TimeProvider
import java.util.Date

class DefaultTimeProvider : TimeProvider {

    override fun getNowMillis(): Long = Date().time
}