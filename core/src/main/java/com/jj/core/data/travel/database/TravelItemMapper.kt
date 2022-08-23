package com.jj.core.data.travel.database

import com.jj.domain.travel.model.TravelItem

fun TravelItemEntity.toTravelItem() = TravelItem(
    id = id,
    listId = listId,
    text = text,
    isChecked = isChecked,
)

fun TravelItem.toTravelItemEntity() = TravelItemEntity(
    id = id,
    listId = listId,
    text = text,
    isChecked = isChecked,
)