package com.jj.core.domain.travel

import com.jj.core.domain.repository.TravelRepository
import com.jj.core.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow

class GetTravelItemsForList(
    private val travelRepository: TravelRepository
) {
    suspend operator fun invoke(listId: String): Flow<List<TravelItem>> = travelRepository.getTravelItemsForListId(listId = listId)
}