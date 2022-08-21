package com.jj.core.data.travel.items

import com.jj.core.domain.travel.model.TravelItem

fun TravelItemEntity.toTravelItem() = TravelItem(
    id = id,
    text = text,
    isChecked = isChecked,
)

fun TravelItem.toTravelItemEntity() = TravelItemEntity(
    id = id,
    text = text,
    isChecked = isChecked,
)