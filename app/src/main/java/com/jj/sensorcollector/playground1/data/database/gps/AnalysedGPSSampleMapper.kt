package com.jj.sensorcollector.playground1.data.database.gps

import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample

fun AnalysedGPSSampleEntity.toAnalysedGPSSample() = AnalysedSample.AnalysedGPSSample(
    latitude = this.latitude,
    longitude = this.longitude,
    sampleTime = this.sampleTime
)

fun AnalysedSample.AnalysedGPSSample.toAnalysedGPSSampleEntity() = AnalysedGPSSampleEntity(
    latitude = this.latitude,
    longitude = this.longitude,
    sampleTime = this.sampleTime
)