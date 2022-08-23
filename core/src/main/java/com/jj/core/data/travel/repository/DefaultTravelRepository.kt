package com.jj.core.data.travel.repository

import com.jj.core.data.travel.database.TravelItemDataDao
import com.jj.core.data.travel.database.toTravelItem
import com.jj.core.data.travel.database.toTravelItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTravelRepository(
    private val travelItemDataDao: TravelItemDataDao
) : com.jj.domain.travel.repository.TravelRepository {

    override suspend fun saveTravelItem(travelItem: com.jj.domain.travel.model.TravelItem) {
        travelItemDataDao.insertTravelItemEntity(travelItem.toTravelItemEntity())
    }

    override suspend fun getTravelItems(): Flow<List<com.jj.domain.travel.model.TravelItem>> =
        travelItemDataDao.getTravelItemEntities().map { entities -> entities.map { entity -> entity.toTravelItem() } }

    override suspend fun getTravelItemsForListId(listId: String): Flow<List<com.jj.domain.travel.model.TravelItem>> =
        travelItemDataDao.getTravelItemEntitiesForListId(listId = listId)
            .map { entities -> entities.map { entity -> entity.toTravelItem() } }

    override suspend fun clearAllTravelItems() = travelItemDataDao.deleteAllTravelItemEntities()

    override suspend fun deleteTravelItem(travelItem: com.jj.domain.travel.model.TravelItem) =
        travelItemDataDao.deleteTravelItemEntity(travelItem.toTravelItemEntity())
}