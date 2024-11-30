package ru.mirea.andreevapk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val id: String,
    val name: String,
    val category: String? = null,
    val area: String? = null,
    val instructions: String? = null,
    val thumbnail: String? = null,
    val tags: List<String>? = null,
    val youtubeLink: String? = null,
    val ingredients: List<String>
) : Parcelable

data class MealShort(
    val id: String,
    val name: String,
    val instructions: String?,
    val thumbnail: String? = null,
)