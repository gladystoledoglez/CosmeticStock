package com.personal.cosmeticstock.mappers

import com.personal.cosmeticstock.entities.ProductEntity
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.extensions.rounded
import com.personal.cosmeticstock.extensions.toInt
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel

fun ProductEntity.toModel() = ProductModel(
    id = id.orZero(),
    name = name.orEmpty(),
    values = toTotalModel(),
    isActive = active.isMoreThanZero()
)

fun ProductEntity.toTotalModel() = TotalModel(
    cost = cost.orZero().toBigDecimal().rounded(),
    sale = sale.orZero().toBigDecimal().rounded(),
    gain = gain.orZero().toBigDecimal().rounded(),
    activeCount = active.orZero()
)

fun ProductModel.toEntity() = ProductEntity(
    id = id.orZero(),
    name = name.orEmpty(),
    cost = values.cost.orZero().toDouble(),
    sale = values.sale.orZero().toDouble(),
    gain = values.gain.orZero().toDouble(),
    active = isActive.toInt()
)

fun List<ProductEntity>.toListModel() = map { it.toModel() }