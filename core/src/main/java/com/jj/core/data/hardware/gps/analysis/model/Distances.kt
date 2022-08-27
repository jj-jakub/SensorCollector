package com.jj.core.data.hardware.gps.analysis.model

data class Distances(
    val currentIntervalDistanceKm: Double,
    val stackedDistanceKm: Double,
    val allSamplesDistanceKm: Double,
)
