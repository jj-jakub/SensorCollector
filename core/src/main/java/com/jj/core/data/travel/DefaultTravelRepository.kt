package com.jj.core.data.travel

import com.jj.core.data.travel.items.TravelItemDataDao
import com.jj.core.data.travel.items.toTravelItem
import com.jj.core.data.travel.items.toTravelItemEntity
import com.jj.core.domain.repository.TravelRepository
import com.jj.core.domain.travel.model.TravelItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTravelRepository(
    private val travelItemDataDao: TravelItemDataDao
) : TravelRepository {

    override suspend fun saveTravelItem(travelItem: TravelItem) {
        travelItemDataDao.insertTravelItemEntity(travelItem.toTravelItemEntity())
    }

    override suspend fun getTravelItems(): Flow<List<TravelItem>> =
        travelItemDataDao.getTravelItemEntities().map { entities -> entities.map { entity -> entity.toTravelItem() } }
}