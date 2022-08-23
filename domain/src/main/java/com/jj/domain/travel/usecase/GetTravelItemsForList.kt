package com.jj.domain.travel.usecase

import com.jj.domain.travel.repository.TravelRepository
import com.jj.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow

class GetTravelItemsForList(
    private val travelRepository: TravelRepository
) {
    suspend operator fun invoke(listId: String): Flow<List<TravelItem>> = travelRepository.getTravelItemsForListId(listId = listId)
}