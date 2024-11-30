package ru.mirea.andreevapk.data.mock

import ru.mirea.andreevapk.domain.model.MealShort


val mockMealList = mutableListOf(
    MealShort(
        id = "1",
        name = "Spaghetti Carbonara",
        instructions = "Pasta, eggs, Parmesan cheese, pancetta, black pepper"
    ),
    MealShort("2", "Chicken Tikka Masala", "Chicken, yogurt, cream, tomatoes, garam masala"),
    MealShort("3", "Caesar Salad", "Romaine lettuce, Caesar dressing, croutons, Parmesan cheese"),
    MealShort("4", "Beef Tacos", "Beef, taco shells, lettuce, tomato, cheddar cheese"),
    MealShort("5", "Vegetable Stir Fry", "Bell peppers, broccoli, soy sauce, ginger, garlic"),
    MealShort("6", "Sushi Rolls", "Sushi rice, nori, fish, cucumber, wasabi"),
    MealShort("7", "Margherita Pizza", "Tomato sauce, mozzarella, basil, olive oil"),
    MealShort(
        "8",
        "Lasagna",
        "Lasagna noodles, ground beef, ricotta cheese, marinara sauce, mozzarella"
    )
)