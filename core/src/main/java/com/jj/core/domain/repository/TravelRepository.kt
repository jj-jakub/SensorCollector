package com.jj.core.domain.repository

import com.jj.core.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun saveTravelItem(travelItem: TravelItem)
    suspend fun getTravelItems(): Flow<List<TravelItem>>
    suspend fun getTravelItemsForListId(listId: String): Flow<List<TravelItem>>
    suspend fun clearAllTravelItems()
    suspend fun deleteTravelItem(travelItem: TravelItem)
}