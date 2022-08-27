package com.jj.core.data.hardware.gps.analysis.model

data class Distances(
    val currentDistanceKm: Double,
    val stackedAverageDistanceKm: Double,
    val allSamplesAverageDistanceKm: Double,
)
