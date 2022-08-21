package com.jj.core.data.travel.items

import com.jj.core.domain.travel.model.TravelItem

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