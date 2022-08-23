package com.jj.domain.travel.usecase

import com.jj.domain.travel.repository.TravelRepository

class ClearAllTravelItems(
    private val travelRepository: TravelRepository,
) {
    suspend operator fun invoke() = travelRepository.clearAllTravelItems()
}