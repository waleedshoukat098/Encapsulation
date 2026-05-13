package com.techinnovation.learning.core.domain

import com.techinnovation.learning.core.model.MealCategory
import com.techinnovation.learning.core.model.MealLog

class ObserveMealsUseCase(private val repository: MealRepository) {
    operator fun invoke(category: MealCategory?, query: String) = repository.meals(category, query)
}

class SaveMealUseCase(private val repository: MealRepository) {
    suspend operator fun invoke(meal: MealLog) = repository.upsertMeal(meal)
}
