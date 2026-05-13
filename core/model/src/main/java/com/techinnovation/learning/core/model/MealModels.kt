package com.techinnovation.learning.core.model

import kotlinx.serialization.Serializable

@Serializable
enum class MealCategory { Breakfast, Lunch, Dinner, Snacks, Scan }

@Serializable
enum class SourceType { Manual, MlKit }

@Serializable
data class MealLog(
    val id: Long,
    val title: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fats: Double,
    val category: MealCategory,
    val timestamp: Long,
    val sourceType: SourceType,
    val confidenceScore: Float,
    val imageUri: String?
)
