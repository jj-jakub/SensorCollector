package com.jj.core.domain.travel

import com.jj.core.domain.repository.TravelRepository
import com.jj.core.domain.travel.model.TravelItem

class SaveTravelItem(
    private val travelRepository: TravelRepository
) {

    suspend operator fun invoke(travelItem: TravelItem) = travelRepository.saveTravelItem(travelItem = travelItem)
}