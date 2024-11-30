package ru.mirea.andreevapk.data.mappers

import ru.mirea.andreevapk.data.model.CategoryData
import ru.mirea.andreevapk.domain.model.Category

object CategoryMapper {
    fun mapToDomain(categoryData: CategoryData): Category {
        return Category(
            id = categoryData.idCategory,
            name = categoryData.strCategory,
            thumbnail = categoryData.strCategoryThumb,
            description = categoryData.strCategoryDescription
        )
    }
}