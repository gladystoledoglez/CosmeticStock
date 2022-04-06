package com.personal.cosmeticstock.mappers

import com.personal.cosmeticstock.entities.ProductEntity
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.extensions.toInt
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel

fun ProductEntity.toModel() = ProductModel(
    id = id.orZero(),
    name = name.orEmpty(),
    values = TotalModel(
        cost = cost.orZero(),
        sale = sale.orZero(),
        gain = gain.orZero()
    ),
    isActive = active.isMoreThanZero()
)

fun ProductEntity.toTotalModel() = TotalModel(
    cost = cost.orZero(),
    sale = sale.orZero(),
    gain = gain.orZero(),
    activeCount = active.orZero()
)

fun ProductModel.toEntity() = ProductEntity(
    id = id.orZero(),
    name = name.orEmpty(),
    cost = values.cost.orZero(),
    sale = values.sale.orZero(),
    gain = values.gain.orZero(),
    active = isActive.toInt()
)

fun List<ProductEntity>.toListModel() = map { it.toModel() }