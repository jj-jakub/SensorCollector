package com.jj.domain.travel.usecase

import com.jj.domain.travel.repository.TravelRepository
import com.jj.domain.travel.model.TravelItem

class SaveTravelItem(
    private val travelRepository: TravelRepository
) {

    suspend operator fun invoke(travelItem: TravelItem) = travelRepository.saveTravelItem(travelItem = travelItem)
}