package com.techinnovation.learning.core.domain

import com.techinnovation.learning.core.model.MealCategory
import com.techinnovation.learning.core.model.MealLog
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun meals(category: MealCategory? = null, query: String = ""): Flow<List<MealLog>>
    suspend fun upsertMeal(meal: MealLog)
    suspend fun deleteMeal(id: Long)
}
