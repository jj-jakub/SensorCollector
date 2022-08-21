package com.jj.core.domain.travel

import com.jj.core.domain.repository.TravelRepository

class ClearAllTravelItems(
    private val travelRepository: TravelRepository,
) {
    suspend operator fun invoke() = travelRepository.clearAllTravelItems()
}