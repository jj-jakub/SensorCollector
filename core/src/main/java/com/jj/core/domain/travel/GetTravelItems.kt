package com.jj.core.domain.travel

import com.jj.core.domain.repository.TravelRepository
import com.jj.core.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow

class GetTravelItems(
    private val travelRepository: TravelRepository,
) {

    suspend operator fun invoke(): Flow<List<TravelItem>> = travelRepository.getTravelItems()
}