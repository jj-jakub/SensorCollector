package com.jj.domain.travel.usecase

import com.jj.domain.travel.repository.TravelRepository
import com.jj.domain.travel.model.TravelItem

class DeleteTravelItem(
    private val travelRepository: TravelRepository,
) {
    suspend operator fun invoke(travelItem: TravelItem) = travelRepository.deleteTravelItem(travelItem = travelItem)
}