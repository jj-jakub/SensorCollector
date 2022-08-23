package com.jj.domain.travel.repository

import kotlinx.coroutines.flow.Flow

interface TravelRepository {
    suspend fun saveTravelItem(travelItem: com.jj.domain.travel.model.TravelItem)
    suspend fun getTravelItems(): Flow<List<com.jj.domain.travel.model.TravelItem>>
    suspend fun getTravelItemsForListId(listId: String): Flow<List<com.jj.domain.travel.model.TravelItem>>
    suspend fun clearAllTravelItems()
    suspend fun deleteTravelItem(travelItem: com.jj.domain.travel.model.TravelItem)
}