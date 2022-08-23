package com.jj.domain.travel.usecase

import com.jj.domain.travel.repository.TravelRepository
import com.jj.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow

class GetAllTravelItems(
    private val travelRepository: TravelRepository,
) {
    suspend operator fun invoke(): Flow<List<TravelItem>> = travelRepository.getTravelItems()
}