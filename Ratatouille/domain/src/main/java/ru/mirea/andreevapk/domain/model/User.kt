package ru.mirea.andreevapk.domain.model

data class User(val id: Int, val name: String?)

const val GUEST_ID = -1
const val GUEST_NAME = "Гость"