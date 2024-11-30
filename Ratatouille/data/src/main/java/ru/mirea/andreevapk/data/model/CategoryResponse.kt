package ru.mirea.andreevapk.data.model

data class CategoryResponse(val categories: List<CategoryData>?)

data class CategoryData(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)