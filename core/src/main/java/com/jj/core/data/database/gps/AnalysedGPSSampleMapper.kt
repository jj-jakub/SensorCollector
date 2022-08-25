package com.jj.core.data.database.gps

import com.jj.domain.model.analysis.AnalysedSample

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